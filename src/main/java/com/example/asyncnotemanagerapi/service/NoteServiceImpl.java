package com.example.asyncnotemanagerapi.service;

import com.example.asyncnotemanagerapi.dto.NoteRequestDTO;
import com.example.asyncnotemanagerapi.dto.NoteResponseDTO;
import com.example.asyncnotemanagerapi.exception.InvalidNoteException;
import com.example.asyncnotemanagerapi.exception.NoteNotFoundException;
import com.example.asyncnotemanagerapi.model.Note;
import com.example.asyncnotemanagerapi.model.backup.BackupStatus;
import com.example.asyncnotemanagerapi.util.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService{
    private final ConcurrentHashMap<Integer, Note> notes = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);
    private final FileService fileService;
    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private volatile BackupStatus lastBackupStatus = BackupStatus.NOT_STARTED;

    public NoteServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    // Yeni not oluştur
    @Override
    public NoteResponseDTO createNote(NoteRequestDTO request) {
        if (request.title() == null || request.title().isBlank()) {
            throw new InvalidNoteException("Note title cannot be empty");
        }

        int id = idCounter.getAndIncrement();
        Note note = NoteMapper.toEntity(request, id);
        notes.put(id, note);

        return NoteMapper.toResponseDTO(note);
    }

    // Tüm notları getir
    @Override
    public List<NoteResponseDTO> getAllNotes() {
        return notes.values()
                .stream()
                .map(NoteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ID'ye göre not getir
    @Override
    public NoteResponseDTO getNoteById(int id) {
        Note note = notes.get(id);
        if (note == null) {
            throw new NoteNotFoundException("Note with ID " + id + " not found");
        }
        return NoteMapper.toResponseDTO(note);
    }

    // Not güncelle
    @Override
    public NoteResponseDTO updateNote(int id, NoteRequestDTO request) {
        Note existing = notes.get(id);
        if (existing == null) {
            throw new NoteNotFoundException("Cannot update. Note with ID " + id + " not found");
        }

        Note updated = existing.updateFrom(request);
        notes.put(id, updated);

        return NoteMapper.toResponseDTO(updated);
    }

    // Not sil
    @Override
    public void deleteNote(int id) {
        if (notes.remove(id) == null) {
            throw new NoteNotFoundException("Cannot delete. Note with ID " + id + " not found");
        }
    }

    // Kategoriye göre filtrele (Stream API)
    @Override
    public List<NoteResponseDTO> filterByCategory(String category) {
        return notes.values().stream()
                .filter(note -> note.category() != null && note.category().equalsIgnoreCase(category))
                .map(NoteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Sıralama kriterine göre sıralama
    @Override
    public List<NoteResponseDTO> sortNotes(String criteria) {
        return notes.values().stream()
                .sorted((n1, n2) -> switch (criteria.toLowerCase()) {
                    case "title" -> n1.title().compareToIgnoreCase(n2.title());
                    case "created" -> n1.createdAt().compareTo(n2.createdAt());
                    default -> n1.id() - n2.id();
                })
                .map(NoteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Asenkron yedekleme işlemi
    @Override
    public CompletableFuture<Void> backupNotesAsync() {
        lastBackupStatus = BackupStatus.IN_PROGRESS;

        return CompletableFuture.runAsync(() -> {
            try {
                fileService.saveNotesToFile(notes.values());
                lastBackupStatus = BackupStatus.SUCCESS;
            } catch (Exception e) {
                lastBackupStatus = BackupStatus.FAILED;
                throw new RuntimeException("Backup failed", e);
            }
        }, executor);
    }

    @Override
    public BackupStatus getBackupStatus() {
        return lastBackupStatus;
    }
}
