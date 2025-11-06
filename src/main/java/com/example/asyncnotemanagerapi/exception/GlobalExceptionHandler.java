// File: src/main/java/com/example/asyncnotemanagerapi/exception/GlobalExceptionHandler.java
package com.example.asyncnotemanagerapi.exception;

import com.example.asyncnotemanagerapi.dto.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoteNotFound(NoteNotFoundException ex) {
        logger.warn("Note not found: {}", ex.getMessage());
        ErrorResponseDTO error = new ErrorResponseDTO("NOTE_NOT_FOUND", ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidNoteException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidNote(InvalidNoteException ex) {
        logger.warn("Invalid note: {}", ex.getMessage());
        ErrorResponseDTO error = new ErrorResponseDTO("INVALID_NOTE", ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponseDTO> handleFileStorage(FileStorageException ex) {
        logger.error("File storage error", ex);
        ErrorResponseDTO error = new ErrorResponseDTO("FILE_STORAGE_ERROR", ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex) {
        logger.error("Unhandled exception", ex);
        ErrorResponseDTO error = new ErrorResponseDTO("GENERIC_ERROR", ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
