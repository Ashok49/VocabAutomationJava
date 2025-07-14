package com.ashokvocab.vocab_automation.repository;

import com.ashokvocab.vocab_automation.model.PodcastVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PodcastVocabularyRepository extends JpaRepository<PodcastVocabulary, Long> {
    List<PodcastVocabulary> findByWordContainingIgnoreCase(String keyword);
}