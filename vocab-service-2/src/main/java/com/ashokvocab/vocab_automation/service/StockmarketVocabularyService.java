// src/main/java/com/ashokvocab/vocab_automation/service/StockmarketVocabularyService.java
package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.model.StockmarketVocabulary;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockmarketVocabularyService {
    List<StockmarketVocabulary> findAll();
    Page<StockmarketVocabulary> findAll(Pageable pageable);
    Optional<StockmarketVocabulary> findById(Long id);
    StockmarketVocabulary save(StockmarketVocabulary vocab);
    void deleteById(Long id);
    List<StockmarketVocabulary> search(String keyword);
    List<StockmarketVocabulary> saveAll(List<StockmarketVocabulary> vocabularies);
}