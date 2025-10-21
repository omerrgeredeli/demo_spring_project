package com.example.asyncnotemanagerapi.service;

import com.example.asyncnotemanagerapi.dto.NoteRequestDTO;
import com.example.asyncnotemanagerapi.dto.NoteResponseDTO;
import com.example.asyncnotemanagerapi.exception.NoteNotFoundException;
import com.example.asyncnotemanagerapi.model.Note;
import com.example.asyncnotemanagerapi.model.backup.BackupStatus;
import com.example.asyncnotemanagerapi.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final FileService fileService;
    private volatile BackupStatus lastBackupStatus = BackupStatus.IDLE;

    public NoteServiceImpl(NoteRepository noteRepository, FileService fileService) {
        this.noteRepository = noteRepository;
        this.fileService = fileService;
    }

    @Override
    public NoteResponseDTO createNote(NoteRequestDTO request) {
        Note note = new Note(
                0,
                request.title(),
                request.content(),
                request.category(),
                request.tags(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Note saved = noteRepository.save(note);
        return toDTO(saved);
    }

    @Override
    public List<NoteResponseDTO> getAllNotes() {
        return noteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NoteResponseDTO getNoteById(int id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note with ID " + id + " not found."));
        return toDTO(note);
    }

    @Override
    public NoteResponseDTO updateNote(int id, NoteRequestDTO request) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note with ID " + id + " not found."));
        Note updated = note.updateFrom(request);
        return toDTO(noteRepository.save(updated));
    }

    @Override
    public void deleteNote(int id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException("Note with ID " + id + " not found.");
        }
        noteRepository.deleteById(id);
    }

    @Override
    public List<NoteResponseDTO> filterByCategory(String category) {
        return noteRepository.findByCategory(category).stream()
                .map(this::toDTO)
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
            comparator = Comparator.comparing(Note::getId); // default
        }
        return noteRepository.findAll().stream()
                .sorted(comparator)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CompletableFuture<Void> backupNotesAsync() {
        lastBackupStatus = BackupStatus.IN_PROGRESS; // Başladı
        return CompletableFuture.runAsync(() -> {
            try {
                fileService.saveNotesToFile(noteRepository.findAll());
                lastBackupStatus = BackupStatus.SUCCESS; // Başarılı
            } catch (Exception e) {
                lastBackupStatus = BackupStatus.FAILURE; // Hata
                System.err.println("Backup failed: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }


    @Override
    public BackupStatus getBackupStatus() {
        return lastBackupStatus;
    }

    private NoteResponseDTO toDTO(Note note) {
        return new NoteResponseDTO(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getCategory(),
                note.getTags(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }

}
