// src/main/java/com/ashokvocab/vocab_automation/service/RacingVocabularyService.java
package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.model.RacingVocabulary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface RacingVocabularyService {
    List<RacingVocabulary> findAll();
    Optional<RacingVocabulary> findById(Long id);
    RacingVocabulary save(RacingVocabulary vocab);
    void deleteById(Long id);
    List<RacingVocabulary> search(String keyword);
    List<RacingVocabulary> saveAll(List<RacingVocabulary> vocabularies);
    Page<RacingVocabulary> findAll(Pageable pageable);
}