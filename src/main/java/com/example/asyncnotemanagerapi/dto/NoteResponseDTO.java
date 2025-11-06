package com.example.asyncnotemanagerapi.dto;

import java.time.LocalDateTime;
import java.util.List;

public record NoteResponseDTO(
        Long id,   // ✅ Long olmalı
        String title,
        String content,
        String category,
        List<String> tags,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}