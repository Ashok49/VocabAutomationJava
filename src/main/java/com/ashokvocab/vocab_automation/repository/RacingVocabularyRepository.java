package com.ashokvocab.vocab_automation.repository;

import com.ashokvocab.vocab_automation.model.RacingVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RacingVocabularyRepository extends JpaRepository<RacingVocabulary, Long> {
    List<RacingVocabulary> findByWordContainingIgnoreCase(String keyword);
}