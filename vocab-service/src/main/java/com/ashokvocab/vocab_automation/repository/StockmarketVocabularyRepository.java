package com.ashokvocab.vocab_automation.repository;

import com.ashokvocab.vocab_automation.model.StockmarketVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockmarketVocabularyRepository extends JpaRepository<StockmarketVocabulary, Long> {
    List<StockmarketVocabulary> findByWordContainingIgnoreCase(String keyword);
}