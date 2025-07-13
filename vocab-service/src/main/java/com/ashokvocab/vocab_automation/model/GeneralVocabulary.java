// src/main/java/com/ashokvocab/vocab_automation/model/GeneralVocabulary.java
package com.ashokvocab.vocab_automation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="general_vocabulary")
public class GeneralVocabulary extends BaseVocabulary {
    public GeneralVocabulary(String word, String meaning) {
        super(word, meaning);
    }

    // Default constructor for JPA
    public GeneralVocabulary() {
        super();
    }
}