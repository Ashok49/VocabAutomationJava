package com.ashokvocab.vocab_automation.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GoogleDriveFile {
    private String id;
    private String name;
    private LocalDateTime modifiedTime;

    // Constructors, getters, setters
    public GoogleDriveFile(String id, String name) {
        this.id = id;
        this.name = name;
        this.modifiedTime = modifiedTime;
    }
    public String getId() { return id; }
    public String getName() { return name; }

    public LocalDateTime getModifiedTime() { return modifiedTime; }

    public boolean isModifiedToday() {
        return modifiedTime != null && modifiedTime.toLocalDate().isEqual(LocalDate.now());
    }
}