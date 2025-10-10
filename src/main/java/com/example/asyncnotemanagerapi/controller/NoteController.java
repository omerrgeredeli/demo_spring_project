package com.example.asyncnotemanagerapi.controller;

import com.example.asyncnotemanagerapi.dto.NoteRequestDTO;
import com.example.asyncnotemanagerapi.dto.NoteResponseDTO;
import com.example.asyncnotemanagerapi.model.backup.BackupStatus;
import com.example.asyncnotemanagerapi.service.NoteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // Tüm notları getir
    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getAllNotes() {
        List<NoteResponseDTO> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    // Tek notu ID ile getir
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> getNoteById(@PathVariable int id) {
        NoteResponseDTO note = noteService.getNoteById(id);
        return ResponseEntity.ok(note);
    }

    // Yeni not oluştur
    @PostMapping
    public ResponseEntity<NoteResponseDTO> createNote(@RequestBody NoteRequestDTO request) {
        NoteResponseDTO createdNote = noteService.createNote(request);
        return ResponseEntity.ok(createdNote);
    }

    // Notu güncelle
    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> updateNote(@PathVariable int id,
                                                      @RequestBody NoteRequestDTO request) {
        NoteResponseDTO updatedNote = noteService.updateNote(id, request);
        return ResponseEntity.ok(updatedNote);
    }

    // Notu sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable int id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    // Kategoriye göre filtrele
    @GetMapping("/filter")
    public ResponseEntity<List<NoteResponseDTO>> filterNotesByCategory(
            @RequestParam String category) {
        List<NoteResponseDTO> filtered = noteService.filterByCategory(category);
        return ResponseEntity.ok(filtered);
    }

    // Sıralama (title veya createdAt)
    @GetMapping("/sort")
    public ResponseEntity<List<NoteResponseDTO>> sortNotes(
            @RequestParam String criteria) {
        List<NoteResponseDTO> sorted = noteService.sortNotes(criteria);
        return ResponseEntity.ok(sorted);
    }

    // Asenkron yedekleme başlat
    @PostMapping("/backup")
    public ResponseEntity<String> backupNotes() {
        noteService.backupNotesAsync();
        return ResponseEntity.ok("Backup started asynchronously");
    }

    // Son yedekleme durumu
    @GetMapping("/backup/status")
    public ResponseEntity<BackupStatus> getBackupStatus() {
        BackupStatus status = noteService.getBackupStatus();
        return ResponseEntity.ok(status);
    }

    // OPTIONS metotları
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> getSupportedMethods() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAllow(Set.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS));
        return ResponseEntity.ok().headers(headers).build();
    }
}
