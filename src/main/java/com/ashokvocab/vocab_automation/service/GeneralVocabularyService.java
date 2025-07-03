// src/main/java/com/ashokvocab/vocab_automation/service/GeneralVocabularyService.java
package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.model.GeneralVocabulary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GeneralVocabularyService {
    List<GeneralVocabulary> findAll();
    Page<GeneralVocabulary> findAll(Pageable pageable);
    Optional<GeneralVocabulary> findById(Long id);
    GeneralVocabulary save(GeneralVocabulary vocab);
    void deleteById(Long id);
    List<GeneralVocabulary> search(String keyword);
    List<GeneralVocabulary> saveAll(List<GeneralVocabulary> vocabularies);
}