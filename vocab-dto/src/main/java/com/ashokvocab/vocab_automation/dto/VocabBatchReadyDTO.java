package com.ashokvocab.vocab_automation.dto;

public class VocabBatchReadyDTO {
    private String audioUrl;
    private String pdfUrl;
    private String userId;

    public VocabBatchReadyDTO() {}

    public VocabBatchReadyDTO(String audioUrl, String pdfUrl, String userId) {
        this.audioUrl = audioUrl;
        this.pdfUrl = pdfUrl;
        this.userId = userId;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VocabBatchReadyDTO{" +
                "audioUrl='" + audioUrl + '\'' +
                ", pdfUrl='" + pdfUrl + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
