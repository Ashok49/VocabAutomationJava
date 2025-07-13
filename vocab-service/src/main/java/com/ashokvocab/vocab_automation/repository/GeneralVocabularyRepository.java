package com.ashokvocab.vocab_automation.repository;

import com.ashokvocab.vocab_automation.model.GeneralVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GeneralVocabularyRepository extends JpaRepository<GeneralVocabulary, Long> {
    List<GeneralVocabulary> findByWordContainingIgnoreCase(String keyword);
}