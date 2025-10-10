package com.example.asyncnotemanagerapi.model;

import com.example.asyncnotemanagerapi.dto.NoteRequestDTO;

import java.time.LocalDateTime;
import java.util.List;

public record Note(int id, String title, String content, String category, List<String> tags, LocalDateTime createdAt, LocalDateTime updatedAt) implements Comparable<Note>{

    @Override
    public int compareTo(Note other){
        return this.title().compareToIgnoreCase(other.title());
    }
    public Note updateFrom(NoteRequestDTO request) {
        return new Note(
                this.id,
                request.title() != null ? request.title() : this.title,
                request.content() != null ? request.content() : this.content,
                request.category() != null ? request.category() : this.category,
                request.tags() != null ? request.tags() : this.tags,
                this.createdAt,
                LocalDateTime.now() // updatedAt güncelleniyor
        );
    }
}

