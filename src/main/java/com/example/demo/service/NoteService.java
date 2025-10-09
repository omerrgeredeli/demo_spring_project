package com.example.demo.service;

import com.example.demo.dto.NoteRequestDTO;
import com.example.demo.dto.NoteResponseDTO;
import com.example.demo.model.Note;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final List<Note> notes = new ArrayList<>();

    public List<NoteResponseDTO> getAllUsers(){
        return notes.stream().map(u -> new NoteResponseDTO(u.id(), u.title(), u.content(), u.createdAt())).toList();
    }

    public Optional<NoteResponseDTO> getNoteById(int id){
        return notes.stream().filter(u -> u.id() == id).findFirst().map(u -> new NoteResponseDTO(u.id(), u.title(), u.content(),u.createdAt()));
    }

    public NoteResponseDTO addNote(NoteRequestDTO request){
        int newId = notes.size() + 1;
        Note newNote = new Note(newId, request.title(), request.content(), LocalDateTime.now());
        notes.add(newNote);
        return new NoteResponseDTO(newNote.id(), newNote.title(), newNote.content(), newNote.createdAt());
    }

    public boolean deleteNote(int id){
        return notes.removeIf(u -> u.id() == id);
    }

    public NoteResponseDTO updateNote(int id, NoteRequestDTO request) {
        for (int i = 0; i < notes.size(); i++) {
            Note existing = notes.get(i);
            if (existing.id() == id) {
                Note newNote = new Note(
                        id,
                        request.title() != null ? request.title() : existing.title(),
                        request.content() != null ? request.content() : existing.content(),
                        existing.createdAt()
                );
                notes.set(i, newNote);
                return new NoteResponseDTO(newNote.id(), newNote.title(), newNote.content(), newNote.createdAt());

            }
        }
        throw new RuntimeException("Note not found with id: " + id);
    }
}
