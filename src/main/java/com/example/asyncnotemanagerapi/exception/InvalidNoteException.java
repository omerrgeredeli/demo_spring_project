package com.example.asyncnotemanagerapi.exception;

public class InvalidNoteException extends RuntimeException{
    public InvalidNoteException(String message){
        super(message);
    }
}
