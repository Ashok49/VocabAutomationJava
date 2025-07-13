// src/main/java/com/ashokvocab/vocab_automation/model/StockmarketVocabulary.java
package com.ashokvocab.vocab_automation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "stockmarket_vocabulary")
public class StockmarketVocabulary extends BaseVocabulary {
    public StockmarketVocabulary(String word, String meaning) {
        super(word, meaning);
    }

    // Default constructor for JPA
    public StockmarketVocabulary() {
        super();
    }
}