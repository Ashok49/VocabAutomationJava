package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.model.SoftwareVocabulary;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SoftwareVocabularyService {
    List<SoftwareVocabulary> findAll();
    Page<SoftwareVocabulary> findAll(Pageable pageable);
    Optional<SoftwareVocabulary> findById(Long id);
    SoftwareVocabulary save(SoftwareVocabulary vocab);
    void deleteById(Long id);
    List<SoftwareVocabulary> search(String keyword);
    List<SoftwareVocabulary> saveAll(List<SoftwareVocabulary> vocabularies);
}