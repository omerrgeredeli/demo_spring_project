package com.example.asyncnotemanagerapi.service;

import com.example.asyncnotemanagerapi.dto.NoteRequestDTO;
import com.example.asyncnotemanagerapi.dto.NoteResponseDTO;
import com.example.asyncnotemanagerapi.model.backup.BackupStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface NoteService {

    // Tüm notları getir (veritabanından)
    List<NoteResponseDTO> getAllNotes();

    // ID ile tek bir not getir
    NoteResponseDTO getNoteById(int id);

    // Yeni not oluştur ve kaydet
    NoteResponseDTO createNote(NoteRequestDTO request);

    // Var olan notu güncelle
    NoteResponseDTO updateNote(int id, NoteRequestDTO request);

    // Notu sil
    void deleteNote(int id);

    // Kategoriye göre filtreleme
    List<NoteResponseDTO> filterByCategory(String category);

    // Başlığa veya tarihe göre sıralama
    List<NoteResponseDTO> sortNotes(String criteria);

    // Asenkron yedekleme başlat
    CompletableFuture<Void> backupNotesAsync();

    // Son yedekleme durumunu döner
    BackupStatus getBackupStatus();
}
