package com.example.asyncnotemanagerapi.model.processor;

import com.example.asyncnotemanagerapi.model.Note;

import java.util.List;
import java.util.stream.Collectors;

public class TextNoteProcessor extends NoteProcessor{
    @Override
    public Note process(Note note) {
        if ("TEXT".equalsIgnoreCase(note.category())) {
            // İçeriği temizle
            String content = note.content();
            if (content != null) {
                content = content.trim().replaceAll("\\s+", " ").toLowerCase();
            }

            // Tag listesi normalize et
            List<String> tags = note.tags();
            if (tags != null) {
                tags = tags.stream()
                        .filter(tag -> tag != null)
                        .map(tag -> tag.trim().toLowerCase())
                        .distinct()
                        .collect(Collectors.toList());
            }

            System.out.println("Text note processed: " + note.title());

            // Yeni Note record döndür
            return new Note(
                    note.id(),
                    note.title(),
                    content,
                    note.category(),
                    tags,
                    note.createdAt(),
                    note.updatedAt()
            );
        } else {
            System.out.println("Skipping non-text note: " + note.title());
            return note; // değişiklik yok
        }
    }
}
