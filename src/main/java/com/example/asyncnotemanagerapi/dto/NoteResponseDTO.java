package com.example.asyncnotemanagerapi.dto;

import java.time.LocalDateTime;
import java.util.List;

public record NoteResponseDTO(
        int id,
        String title,
        String content,
        String category,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
