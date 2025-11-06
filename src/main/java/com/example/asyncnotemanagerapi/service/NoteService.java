package com.example.asyncnotemanagerapi.service;

import com.example.asyncnotemanagerapi.dto.NoteRequestDTO;
import com.example.asyncnotemanagerapi.dto.NoteResponseDTO;
import com.example.asyncnotemanagerapi.model.backup.BackupStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface NoteService {

    List<NoteResponseDTO> getAllNotes();

    NoteResponseDTO getNoteById(Long id);

    NoteResponseDTO createNote(NoteRequestDTO request);

    NoteResponseDTO updateNote(Long id, NoteRequestDTO request);

    void deleteNote(Long id);

    List<NoteResponseDTO> filterByCategory(String category);

    List<NoteResponseDTO> sortNotes(String criteria);

    CompletableFuture<Void> backupNotesAsync();

    BackupStatus getBackupStatus();
}
