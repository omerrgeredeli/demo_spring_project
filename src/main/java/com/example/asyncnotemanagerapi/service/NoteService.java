package com.example.asyncnotemanagerapi.service;

import com.example.asyncnotemanagerapi.dto.NoteRequestDTO;
import com.example.asyncnotemanagerapi.dto.NoteResponseDTO;
import com.example.asyncnotemanagerapi.model.backup.BackupStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface NoteService {
    List<NoteResponseDTO> getAllNotes();

    NoteResponseDTO getNoteById(int id);

    NoteResponseDTO createNote(NoteRequestDTO request);

    NoteResponseDTO updateNote(int id, NoteRequestDTO request);

    void deleteNote(int id);

    List<NoteResponseDTO> filterByCategory(String category);

    List<NoteResponseDTO> sortNotes(String criteria);

    CompletableFuture<Void> backupNotesAsync();

    BackupStatus getBackupStatus();
}
