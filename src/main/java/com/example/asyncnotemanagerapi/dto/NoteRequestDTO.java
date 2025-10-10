package com.example.asyncnotemanagerapi.dto;

import java.util.List;

public record NoteRequestDTO(String title, String content, String category, List<String> tags) {}
