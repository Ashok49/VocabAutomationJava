package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.model.SoftwareVocabulary;
import java.util.List;
import java.util.Optional;

public interface SoftwareVocabularyService {
    List<SoftwareVocabulary> findAll();
    Optional<SoftwareVocabulary> findById(Long id);
    SoftwareVocabulary save(SoftwareVocabulary vocab);
    void deleteById(Long id);
    List<SoftwareVocabulary> search(String keyword);
    List<SoftwareVocabulary> saveAll(List<SoftwareVocabulary> vocabularies);
}