package com.example.asyncnotemanagerapi.dto;

import com.example.asyncnotemanagerapi.model.backup.BackupStatus;

import java.time.LocalDateTime;

public record BackupResponseDTO (BackupStatus backupStatus, String message, LocalDateTime timestamp){}
