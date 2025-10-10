package com.example.asyncnotemanagerapi.config;

import com.example.asyncnotemanagerapi.model.Note;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AppStartupRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("✅ AsyncNoteManagerAPI started successfully at " + LocalDateTime.now());

        // Örnek test verisi oluşturulabilir (ileride in-memory list’e eklenecek)
        Note sampleNote = new Note(
                1,
                "Welcome Note",
                "This is your first note created automatically.",
                "General",
                List.of("intro", "welcome"),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        System.out.println("Sample Note Initialized: " + sampleNote.title());
    }
}
