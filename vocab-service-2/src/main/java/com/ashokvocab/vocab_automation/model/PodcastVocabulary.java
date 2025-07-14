// src/main/java/com/ashokvocab/vocab_automation/model/PodcastVocabulary.java
package com.ashokvocab.vocab_automation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "podcast_vocabulary")
public class PodcastVocabulary extends BaseVocabulary {
    public PodcastVocabulary(String word, String meaning) {
        super(word, meaning);
    }

    // Default constructor for JPA
    public PodcastVocabulary() {
        super();
    }
}