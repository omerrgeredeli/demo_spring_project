package com.example.asyncnotemanagerapi.util;

import com.example.asyncnotemanagerapi.dto.NoteRequestDTO;
import com.example.asyncnotemanagerapi.dto.NoteResponseDTO;
import com.example.asyncnotemanagerapi.model.Note;

import java.time.LocalDateTime;

public class NoteMapper {
    // Request DTO -> Entity
    public static Note toEntity(NoteRequestDTO dto, int generatedId) {
        return new Note(
                generatedId,
                dto.title(),
                dto.content(),
                dto.category(),
                dto.tags(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    // Entity -> Response DTO
    // Entity -> Response DTO
    public static NoteResponseDTO toResponseDTO(Note note) {
        return new NoteResponseDTO(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getCategory(),
                note.getTags(),       // 👈 eksik olan buydu
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }

}
