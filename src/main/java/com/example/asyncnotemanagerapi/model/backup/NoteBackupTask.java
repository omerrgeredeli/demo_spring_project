package com.example.asyncnotemanagerapi.model.backup;

import com.example.asyncnotemanagerapi.service.FileService;
import com.example.asyncnotemanagerapi.model.Note;
import java.util.List;

public class NoteBackupTask implements Runnable{
    private final List<Note> notes;
    private final FileService fileService;

    public NoteBackupTask(List<Note> notes, FileService fileService){
        this.notes = notes;
        this.fileService = fileService;
    }

    @Override
    public void run(){
        try{
            fileService.saveNotesToFile(notes);
            System.out.println("Backup completed successfull!");
        }catch (Exception e){
            System.out.println("Backup failed: " + e.getMessage());
        }
    }
}
