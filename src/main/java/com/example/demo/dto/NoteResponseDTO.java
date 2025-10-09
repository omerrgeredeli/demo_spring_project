package com.example.demo.dto;

import java.time.LocalDateTime;

public record NoteResponseDTO(int id, String title, String content, LocalDateTime createdAt) {}
