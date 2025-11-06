package com.example.asyncnotemanagerapi.service;

import com.example.asyncnotemanagerapi.dto.NoteRequestDTO;
import com.example.asyncnotemanagerapi.dto.NoteResponseDTO;
import com.example.asyncnotemanagerapi.exception.NoteNotFoundException;
import com.example.asyncnotemanagerapi.model.Note;
import com.example.asyncnotemanagerapi.model.backup.BackupStatus;
import com.example.asyncnotemanagerapi.repository.NoteRepository;
import com.example.asyncnotemanagerapi.util.NoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    private final NoteRepository noteRepository;
    private final FileService fileService;
    private final AtomicReference<BackupStatus> lastBackupStatus = new AtomicReference<>(BackupStatus.IDLE);

    public NoteServiceImpl(NoteRepository noteRepository, FileService fileService) {
        this.noteRepository = noteRepository;
        this.fileService = fileService;
    }

    @Override
    public NoteResponseDTO createNote(NoteRequestDTO request) {
        Note toSave = NoteMapper.toEntity(request);
        Note saved = noteRepository.save(toSave);
        return NoteMapper.toResponseDTO(saved);
    }

    @Override
    public List<NoteResponseDTO> getAllNotes() {
        return noteRepository.findAll().stream()
                .map(NoteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NoteResponseDTO getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note with ID " + id + " not found."));
        return NoteMapper.toResponseDTO(note);
    }

    @Override
    public NoteResponseDTO updateNote(Long id, NoteRequestDTO request) {
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note with ID " + id + " not found."));
        Note updated = NoteMapper.applyUpdates(existing, request);
        updated = noteRepository.save(updated);
        return NoteMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException("Note with ID " + id + " not found.");
        }
        noteRepository.deleteById(id);
    }

    @Override
    public List<NoteResponseDTO> filterByCategory(String category) {
        return noteRepository.findByCategory(category).stream()
                .map(NoteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoteResponseDTO> sortNotes(String criteria) {
        Comparator<Note> comparator;
        if ("title".equalsIgnoreCase(criteria)) {
            comparator = Comparator.comparing(Note::getTitle, String.CASE_INSENSITIVE_ORDER);
        } else if ("createdAt".equalsIgnoreCase(criteria)) {
            comparator = Comparator.comparing(Note::getCreatedAt);
        } else {
            comparator = Comparator.comparing(Note::getId);
        }
        return noteRepository.findAll().stream()
                .sorted(comparator)
                .map(NoteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Async("noteExecutor")
    public CompletableFuture<Void> backupNotesAsync() {
        lastBackupStatus.set(BackupStatus.IN_PROGRESS);
        return CompletableFuture.runAsync(() -> {
            try {
                fileService.saveNotesToFile(noteRepository.findAll());
                lastBackupStatus.set(BackupStatus.SUCCESS);
                logger.info("Backup completed successfully");
            } catch (Exception e) {
                lastBackupStatus.set(BackupStatus.FAILURE);
                logger.error("Backup failed", e);
            }
        });
    }

    @Override
    public BackupStatus getBackupStatus() {
        return lastBackupStatus.get();
    }
}
