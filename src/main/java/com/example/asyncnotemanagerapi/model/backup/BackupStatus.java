package com.example.asyncnotemanagerapi.model.backup;

public enum BackupStatus {
    IDLE("Idle"),
    IN_PROGRESS("In Progress"),
    SUCCESS("Success"),
    FAILURE("Failure");  // NoteServiceImpl’deki "lastBackupStatus = BackupStatus.FAILURE" için

    private final String value;

    BackupStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // String’den enum bulmak için opsiyonel
    public static BackupStatus fromValue(String value) {
        for (BackupStatus status : values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown BackupStatus: " + value);
    }
}
