package com.example.asyncnotemanagerapi.util;

import com.example.asyncnotemanagerapi.dto.NoteRequestDTO;
import com.example.asyncnotemanagerapi.dto.NoteResponseDTO;
import com.example.asyncnotemanagerapi.model.Note;

import java.time.LocalDateTime;
import java.util.List;

public class NoteMapper {

    public static NoteResponseDTO toResponseDTO(Note note) {
        return new NoteResponseDTO(
                note.getId(), // ✅ Long id (DTO’da da Long olmalı)
                note.getTitle(),
                note.getContent(),
                note.getCategory(),
                note.getTags(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }

    // ✅ DTO -> Entity dönüşümü
    public static Note toEntity(NoteRequestDTO dto) {
        return Note.builder()
                .title(dto.title())
                .content(dto.content())
                .category(dto.category())
                .tags(dto.tags())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // ✅ Var olan notu DTO bilgileriyle güncelle
    public static Note applyUpdates(Note existing, NoteRequestDTO dto) {
        if (dto.title() != null) existing.setTitle(dto.title());
        if (dto.content() != null) existing.setContent(dto.content());
        if (dto.category() != null) existing.setCategory(dto.category());
        if (dto.tags() != null) existing.setTags(dto.tags());
        existing.setUpdatedAt(LocalDateTime.now());
        return existing;
    }
}
