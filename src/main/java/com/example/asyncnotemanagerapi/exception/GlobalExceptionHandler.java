package com.example.asyncnotemanagerapi.exception;

import com.example.asyncnotemanagerapi.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoteNotFound(NoteNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                "NOTE_NOT_FOUND",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidNoteException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidNote(InvalidNoteException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                "INVALID_NOTE",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponseDTO> handleFileStorage(FileStorageException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                "FILE_STORAGE_ERROR",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                "GENERIC_ERROR",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
