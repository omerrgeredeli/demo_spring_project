package com.example.asyncnotemanagerapi.dto;

import java.time.LocalDateTime;

public record NoteResponseDTO(int id, String title, String content, String category, LocalDateTime createdAt, LocalDateTime updatedAt) {}
