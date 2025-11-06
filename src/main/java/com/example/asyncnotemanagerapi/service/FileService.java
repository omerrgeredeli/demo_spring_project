// File: src/main/java/com/example/asyncnotemanagerapi/service/FileService.java
package com.example.asyncnotemanagerapi.service;

import com.example.asyncnotemanagerapi.exception.FileStorageException;
import com.example.asyncnotemanagerapi.model.Note;
import com.example.asyncnotemanagerapi.util.FileUtils;
import com.example.asyncnotemanagerapi.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Collection;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    private final Path filePath = Path.of(AppConstants.DEFAULT_BACKUP_FILE);

    public void saveNotesToFile(Collection<Note> notes) {
        try {
            createBackupDirectoryIfNeeded();
            StringBuilder sb = new StringBuilder();

            for (Note note : notes) {
                sb.append("ID: ").append(note.getId()).append("\n")
                        .append("Title: ").append(note.getTitle()).append("\n")
                        .append("Category: ").append(note.getCategory()).append("\n")
                        .append("CreatedAt: ").append(note.getCreatedAt()).append("\n")
                        .append("UpdatedAt: ").append(note.getUpdatedAt()).append("\n")
                        .append("Content: ").append(note.getContent()).append("\n")
                        .append("Tags: ").append(note.getTags()).append("\n")
                        .append("------------\n");
            }

            FileUtils.writeToFile(filePath.toString(), sb.toString(), false);
            logger.info("Saved {} notes to {}", notes.size(), filePath);
        } catch (Exception e) {
            logger.error("Failed to save notes to file", e);
            throw new FileStorageException("Failed to save notes to file", e);
        }
    }

    private void createBackupDirectoryIfNeeded() {
        FileUtils.createDirectory(filePath.getParent().toString());
    }
}
