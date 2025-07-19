package com.ashokvocab.dto;

public class QuizReadyEventDTO {
    private String quizId;
    private String message;

    public QuizReadyEventDTO() {}

    public QuizReadyEventDTO(String quizId, String message) {
        this.quizId = quizId;
        this.message = message;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

