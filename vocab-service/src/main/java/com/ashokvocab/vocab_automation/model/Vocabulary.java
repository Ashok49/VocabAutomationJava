package com.ashokvocab.vocab_automation.model;

public class Vocabulary {
    private String word;
    private String meaning;

    public Vocabulary(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public String getWord() { return word; }
    public String getMeaning() { return meaning; }
}