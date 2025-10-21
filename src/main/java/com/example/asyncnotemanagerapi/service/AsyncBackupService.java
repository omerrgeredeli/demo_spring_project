package com.example.asyncnotemanagerapi.service;

import org.springframework.stereotype.Service;

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

    /**
     * Belirli aralıklarla periyodik backup başlatır.
     *
     * @param intervalSeconds Backup aralığı saniye cinsinden.
     */
    public void startPeriodicBackup(long intervalSeconds) {
        scheduler.scheduleAtFixedRate(() -> {
            noteService.backupNotesAsync()
                    .exceptionally(ex -> {
                        System.err.println("Scheduled backup failed: " + ex.getMessage());
                        ex.printStackTrace();
                        return null;
                    });
        }, 0, intervalSeconds, TimeUnit.SECONDS);

    }

    /**
     * Backup scheduler'ını durdurur.
     */
    public void stopBackup() {
        scheduler.shutdownNow();
        System.out.println("Periodic backup stopped");
    }
}
