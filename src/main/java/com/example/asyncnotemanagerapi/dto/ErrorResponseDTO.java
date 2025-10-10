package com.example.asyncnotemanagerapi.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(String errorCode, String message, LocalDateTime timestamp) {}
