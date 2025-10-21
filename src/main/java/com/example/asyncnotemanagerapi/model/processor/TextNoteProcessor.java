package com.example.asyncnotemanagerapi.model.processor;

import com.example.asyncnotemanagerapi.model.Note;

import java.util.List;
import java.util.stream.Collectors;

public class TextNoteProcessor extends NoteProcessor{
    @Override
    public Note process(Note note) {
        if ("TEXT".equalsIgnoreCase(note.getCategory())) {
            // İçeriği temizle
            String content = note.getContent();
            if (content != null) {
                content = content.trim().replaceAll("\\s+", " ").toLowerCase();
            }

            // Tag listesi normalize et
            List<String> tags = note.getTags();
            if (tags != null) {
                tags = tags.stream()
                        .filter(tag -> tag != null)
                        .map(tag -> tag.trim().toLowerCase())
                        .distinct()
                        .collect(Collectors.toList());
            }

            System.out.println("Text note processed: " + note.getTitle());

            // Yeni Note record döndür
            return new Note(
                    note.getId(),
                    note.getTitle(),
                    content,
                    note.getCategory(),
                    tags,
                    note.getCreatedAt(),
                    note.getUpdatedAt()
            );
        } else {
            System.out.println("Skipping non-text note: " + note.getTitle());
            return note; // değişiklik yok
        }
    }
}
