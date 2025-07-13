// src/main/java/com/ashokvocab/vocab_automation/model/RacingVocabulary.java
package com.ashokvocab.vocab_automation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "racing_vocabulary")
public class RacingVocabulary extends BaseVocabulary {
    public RacingVocabulary(String word, String meaning) {
        super(word, meaning);
    }

    // Default constructor for JPA
    public RacingVocabulary() {
        super();
    }
}