package com.ashokvocab.vocab_automation.dto;

    public class VocabBatchReadyDTO {
        private String audioUrl;
        private String pdfUrl;
        private String userId;
        private String email;
        private String phone;

        public VocabBatchReadyDTO() {}

        public VocabBatchReadyDTO(String audioUrl, String pdfUrl, String userId, String email, String phone) {
            this.audioUrl = audioUrl;
            this.pdfUrl = pdfUrl;
            this.userId = userId;
            this.email = email;
            this.phone = phone;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "VocabBatchReadyDTO{" +
                    "audioUrl='" + audioUrl + '\'' +
                    ", pdfUrl='" + pdfUrl + '\'' +
                    ", userId='" + userId + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }