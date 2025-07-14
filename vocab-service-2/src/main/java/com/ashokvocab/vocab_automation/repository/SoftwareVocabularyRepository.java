package com.ashokvocab.vocab_automation.repository;

import com.ashokvocab.vocab_automation.model.SoftwareVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SoftwareVocabularyRepository extends JpaRepository<SoftwareVocabulary, Long> {
    List<SoftwareVocabulary> findByWordContainingIgnoreCase(String keyword);
    List<SoftwareVocabulary> findByWordContainingIgnoreCaseOrMeaningContainingIgnoreCase(String word, String meaning);
}
