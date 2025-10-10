package com.example.asyncnotemanagerapi.service;

import com.example.asyncnotemanagerapi.exception.FileStorageException;
import com.example.asyncnotemanagerapi.model.Note;
import com.example.asyncnotemanagerapi.util.FileUtils;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class AsyncBackupService {
    private final NoteService noteService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public AsyncBackupService(NoteService noteService) {
        this.noteService = noteService;
    }

    // Belirli aralıklarla backup yap
    public void startPeriodicBackup(long intervalSeconds) {
        scheduler.scheduleAtFixedRate(
                () -> noteService.backupNotesAsync(),
                0,
                intervalSeconds,
                TimeUnit.SECONDS
        );
        System.out.println("Periodic backup started every " + intervalSeconds + " seconds");
    }

    // Backup scheduler'ı kapat
    public void stopBackup() {
        scheduler.shutdownNow();
        System.out.println("Periodic backup stopped");
    }
}
