// src/main/java/com/ashokvocab/vocab_automation/model/TravelVocabulary.java
package com.ashokvocab.vocab_automation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "travel_vocabulary")
public class TravelVocabulary extends BaseVocabulary {
    public TravelVocabulary(String word, String meaning) {
        super(word, meaning);
    }

    // Default constructor for JPA
    public TravelVocabulary() {
        super();
    }
}