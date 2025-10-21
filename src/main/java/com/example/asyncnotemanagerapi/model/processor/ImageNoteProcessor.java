package com.example.asyncnotemanagerapi.model.processor;

import com.example.asyncnotemanagerapi.model.Note;

public class ImageNoteProcessor extends NoteProcessor{
    @Override
    public Note process(Note note) {
        if ("IMAGE".equalsIgnoreCase(note.getCategory())) {
            System.out.println("Image note processed: " + note.getTitle());
        } else {
            System.out.println("Skipping non-image note: " + note.getTitle());
        }
        return note; // immutable olduğu için değişiklik yok
    }
}
