package com.ashokvocab.vocab_automation.repository;

import com.ashokvocab.vocab_automation.model.TravelVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TravelVocabularyRepository extends JpaRepository<TravelVocabulary, Long> {
    List<TravelVocabulary> findByWordContainingIgnoreCase(String keyword);
}