package com.example.asyncnotemanagerapi.repository;

import com.example.asyncnotemanagerapi.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    // Kategoriye göre filtreleme
    List<Note> findByCategory(String category);
}
