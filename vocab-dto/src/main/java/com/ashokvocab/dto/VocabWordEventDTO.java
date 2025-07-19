package com.ashokvocab.dto;

public class VocabWordEventDTO {
    private String word;
    private String meaning;

    public VocabWordEventDTO() {}

    public VocabWordEventDTO(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
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
}

