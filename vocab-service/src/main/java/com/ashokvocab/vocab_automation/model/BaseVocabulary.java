package com.ashokvocab.vocab_automation.model;

    import jakarta.persistence.*;
    import java.time.LocalDateTime;

    @MappedSuperclass
    public abstract class BaseVocabulary {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String word;

        private String meaning;

        @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
        private LocalDateTime createdDate;

        @Column(name = "sent_date")
        private LocalDateTime sentDate;

        // Default constructor for JPA
        public BaseVocabulary() {
        }

        // Parameterized constructor for subclasses
        public BaseVocabulary(String word, String meaning) {
            this.word = word;
            this.meaning = meaning;
        }

        // Getters and setters...
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getMeaning() {
            return meaning;
        }

        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }

        public LocalDateTime getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
        }

        public LocalDateTime getSentDate() {
            return sentDate;
        }

        public void setSentDate(LocalDateTime sentDate) {
            this.sentDate = sentDate;
        }
    }