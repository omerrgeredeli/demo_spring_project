package com.example.asyncnotemanagerapi.util;

import com.example.asyncnotemanagerapi.exception.FileStorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtils {
    // Create directory if not exists
    public static void createDirectory(String path) {
        try {
            Path dirPath = Path.of(path);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
        } catch (IOException e) {
            throw new FileStorageException("Failed to create directory: " + path, e);
        }
    }

    // Write content to a file
    public static void writeToFile(String filename, String content) {
        try {
            Path filePath = Path.of(filename);
            Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException("Failed to write to file: " + filename, e);
        }
    }

    // Read content from a file
    public static String readFromFile(String filename) {
        try {
            Path filePath = Path.of(filename);
            if (!Files.exists(filePath)) {
                throw new FileStorageException("File not found: " + filename);
            }
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new FileStorageException("Failed to read file: " + filename, e);
        }
    }
}
