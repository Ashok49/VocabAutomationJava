package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.model.TravelVocabulary;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TravelVocabularyService {
    List<TravelVocabulary> findAll();
    Page<TravelVocabulary> findAll(Pageable pageable);
    Optional<TravelVocabulary> findById(Long id);
    TravelVocabulary save(TravelVocabulary vocab);
    void deleteById(Long id);
    List<TravelVocabulary> search(String keyword);
    List<TravelVocabulary> saveAll(List<TravelVocabulary> vocabularies);
}