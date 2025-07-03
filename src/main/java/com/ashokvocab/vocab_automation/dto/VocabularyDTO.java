// VocabularyDTO.java
package com.ashokvocab.vocab_automation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VocabularyDTO {
    @JsonProperty("Word")
    private String word;

    @JsonProperty("Meaning")
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