// VocabularyDTO.java
package com.ashokvocab.vocab_automation.model;

public class VocabularyDTO {
    private String word;
    private String meaning;

    public VocabularyDTO(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    // getters and setters
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

}