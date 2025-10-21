package com.example.asyncnotemanagerapi.model;

import com.example.asyncnotemanagerapi.dto.NoteRequestDTO;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notes")
public class Note implements Comparable<Note> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String content;
    private String category;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "note_tags", joinColumns = @JoinColumn(name = "note_id"))
    @Column(name = "tag")
    private List<String> tags;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Note() {}

    public Note(int id, String title, String content, String category, List<String> tags, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public int compareTo(Note other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    public Note updateFrom(NoteRequestDTO request) {
        return new Note(
                this.id,
                request.title() != null ? request.title() : this.title,
                request.content() != null ? request.content() : this.content,
                request.category() != null ? request.category() : this.category,
                request.tags() != null ? request.tags() : this.tags,
                this.createdAt,
                LocalDateTime.now()
        );
    }
}
