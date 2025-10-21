package com.example.asyncnotemanagerapi.util;

import com.example.asyncnotemanagerapi.model.Note;

import java.util.Comparator;

public class NoteComparator implements Comparator<Note> {
    @Override
    public int compare(Note n1, Note n2) {
        // Null kontrolü – güvenli karşılaştırma
        if (n1 == null || n1.getCreatedAt() == null) return -1;
        if (n2 == null || n2.getCreatedAt() == null) return 1;

        // Tarihe göre karşılaştırma (artan sırada)
        return n1.getCreatedAt().compareTo(n2.getCreatedAt());
    }
}
