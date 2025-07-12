package com.ashokvocab.vocab_automation.dto;

    import java.util.List;
    import java.util.Map;

    public class TodayVocabularyResponse {
        private List<VocabularyDTO> vocabulary;
        private Map<String, String> stories;
        private String audioUrl;
        private String pdfUrl;

        public TodayVocabularyResponse() {}

        public TodayVocabularyResponse(List<VocabularyDTO> vocabulary, Map<String, String> stories, String audioUrl, String pdfUrl) {
            this.vocabulary = vocabulary;
            this.stories = stories;
            this.audioUrl = audioUrl;
            this.pdfUrl = pdfUrl;
        }

        public List<VocabularyDTO> getVocabulary() {
            return vocabulary;
        }

        public void setVocabulary(List<VocabularyDTO> vocabulary) {
            this.vocabulary = vocabulary;
        }

        public Map<String, String> getStories() {
            return stories;
        }

        public void setStories(Map<String, String> stories) {
            this.stories = stories;
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
    }