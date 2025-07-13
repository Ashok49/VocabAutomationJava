// src/main/java/com/ashokvocab/vocab_automation/model/SoftwareVocabulary.java
package com.ashokvocab.vocab_automation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "software_vocabulary")
public class SoftwareVocabulary extends BaseVocabulary {
    public SoftwareVocabulary(String word, String meaning) {
        super(word, meaning);
    }

    // Default constructor for JPA
    public SoftwareVocabulary() {
        super();
    }
}