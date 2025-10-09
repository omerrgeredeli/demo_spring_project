package com.example.demo.controller;

import com.example.demo.dto.NoteRequestDTO;
import com.example.demo.dto.NoteResponseDTO;
import com.example.demo.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getUsers(){
        return ResponseEntity.ok(noteService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> getNoteById(@PathVariable int id){
        return noteService.getNoteById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NoteResponseDTO> createNote(@RequestBody NoteRequestDTO request){
        NoteResponseDTO created = noteService.addNote(request);
        return ResponseEntity.status(201).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable int id){
        boolean deleted = noteService.deleteNote(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> updateNote(@PathVariable int id, @RequestBody NoteRequestDTO request){
        NoteResponseDTO updated = noteService.updateNote(id,request);
        return ResponseEntity.ok(updated);
    }


}
