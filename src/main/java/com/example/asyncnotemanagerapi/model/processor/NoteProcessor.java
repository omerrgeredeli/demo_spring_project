package com.example.asyncnotemanagerapi.model.processor;

import com.example.asyncnotemanagerapi.model.Note;

public abstract class NoteProcessor {
    public abstract Note process(Note note);
}
