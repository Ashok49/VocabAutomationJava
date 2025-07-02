// src/main/java/com/ashokvocab/vocab_automation/service/impl/StockmarketVocabularyServiceImpl.java
package com.ashokvocab.vocab_automation.service.impl;

import com.ashokvocab.vocab_automation.model.StockmarketVocabulary;
import com.ashokvocab.vocab_automation.repository.StockmarketVocabularyRepository;
import com.ashokvocab.vocab_automation.service.StockmarketVocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockmarketVocabularyServiceImpl implements StockmarketVocabularyService {

    @Autowired
    private StockmarketVocabularyRepository stockmarketVocabularyRepository;

    @Override
    public List<StockmarketVocabulary> findAll() {
        return stockmarketVocabularyRepository.findAll();
    }

    @Override
    public Optional<StockmarketVocabulary> findById(Long id) {
        return stockmarketVocabularyRepository.findById(id);
    }

    @Override
    public StockmarketVocabulary save(StockmarketVocabulary vocab) {
        return stockmarketVocabularyRepository.save(vocab);
    }

    @Override
    public void deleteById(Long id) {
        stockmarketVocabularyRepository.deleteById(id);
    }

    @Override
    public List<StockmarketVocabulary> search(String keyword) {
        return stockmarketVocabularyRepository.findByWordContainingIgnoreCase(keyword);
    }

    @Override
    public List<StockmarketVocabulary> saveAll(List<StockmarketVocabulary> vocabularies) {
        return stockmarketVocabularyRepository.saveAll(vocabularies);
    }
}