package com.ashokvocab.dto;

public class StreakEventDTO {
    private int streakCount;
    private String message;

    public StreakEventDTO() {}

    public StreakEventDTO(int streakCount, String message) {
        this.streakCount = streakCount;
        this.message = message;
    }

    public int getStreakCount() {
        return streakCount;
    }

    public void setStreakCount(int streakCount) {
        this.streakCount = streakCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

