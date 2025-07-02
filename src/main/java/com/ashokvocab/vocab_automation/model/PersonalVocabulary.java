// src/main/java/com/ashokvocab/vocab_automation/model/PersonalVocabulary.java
package com.ashokvocab.vocab_automation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "personal_vocabulary")
public class PersonalVocabulary extends BaseVocabulary {
    public PersonalVocabulary(String word, String meaning) {
        super(word, meaning);
    }

    // Default constructor for JPA
    public PersonalVocabulary() {
        super();
    }
}