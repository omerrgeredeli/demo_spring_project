package com.example.asyncnotemanagerapi.service;

import com.example.asyncnotemanagerapi.exception.FileStorageException;
import com.example.asyncnotemanagerapi.model.Note;
import com.example.asyncnotemanagerapi.util.FileUtils;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Collection;

@Service
public class FileService {
    private final Path filePath = Path.of("backups/notes_backup.txt");

    public void saveNotesToFile(Collection<Note> notes) {
        try {
            createBackupDirectoryIfNeeded();
            StringBuilder sb = new StringBuilder();

            for (Note note : notes) {
                sb.append("ID: ").append(note.id()).append("\n")
                        .append("Title: ").append(note.title()).append("\n")
                        .append("Category: ").append(note.category()).append("\n")
                        .append("CreatedAt: ").append(note.createdAt()).append("\n")
                        .append("UpdatedAt: ").append(note.updatedAt()).append("\n")
                        .append("Content: ").append(note.content()).append("\n")
                        .append("Tags: ").append(note.tags()).append("\n")
                        .append("------------\n");
            }

            FileUtils.writeToFile(filePath.toString(), sb.toString());
        } catch (Exception e) {
            throw new FileStorageException("Failed to save notes to file", e);
        }
    }

    public void createBackupDirectoryIfNeeded() {
        FileUtils.createDirectory(filePath.getParent().toString());
    }
}
