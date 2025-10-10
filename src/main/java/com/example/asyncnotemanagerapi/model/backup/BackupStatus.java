package com.example.asyncnotemanagerapi.model.backup;

public enum BackupStatus {
    NOT_STARTED("Not Started"),
    IN_PROGRESS("In Progress"),
    SUCCESS("Success"),
    FAILED("Failed");

    private final String value;

    BackupStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // Opsiyonel: String’den enum bulmak için
    public static BackupStatus fromValue(String value) {
        for (BackupStatus status : values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown BackupStatus: " + value);
    }
}
