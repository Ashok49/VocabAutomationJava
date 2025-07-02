// src/main/java/com/ashokvocab/vocab_automation/service/PodcastVocabularyService.java
package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.model.PodcastVocabulary;
import java.util.List;
import java.util.Optional;

public interface PodcastVocabularyService {
    List<PodcastVocabulary> findAll();
    Optional<PodcastVocabulary> findById(Long id);
    PodcastVocabulary save(PodcastVocabulary vocab);
    void deleteById(Long id);
    List<PodcastVocabulary> search(String keyword);
    List<PodcastVocabulary> saveAll(List<PodcastVocabulary> vocabularies);
}