// File: src/main/java/com/example/asyncnotemanagerapi/util/FileUtils.java
package com.example.asyncnotemanagerapi.util;

import com.example.asyncnotemanagerapi.exception.FileStorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtils {

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

    /**
     * @param filename destination file path
     * @param content content to write
     * @param append if true, appends to file; otherwise truncates/overwrites
     */
    public static void writeToFile(String filename, String content, boolean append) {
        try {
            Path filePath = Path.of(filename);
            if (append) {
                Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } else {
                Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            throw new FileStorageException("Failed to write to file: " + filename, e);
        }
    }

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
