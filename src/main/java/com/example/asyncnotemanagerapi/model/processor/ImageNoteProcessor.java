package com.example.asyncnotemanagerapi.model.processor;

import com.example.asyncnotemanagerapi.model.Note;

public class ImageNoteProcessor extends NoteProcessor{
    @Override
    public Note process(Note note) {
        if ("IMAGE".equalsIgnoreCase(note.category())) {
            System.out.println("Image note processed: " + note.title());
        } else {
            System.out.println("Skipping non-image note: " + note.title());
        }
        return note; // immutable olduğu için değişiklik yok
    }
}
