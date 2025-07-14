package com.ashokvocab.vocab_automation.repository;

import com.ashokvocab.vocab_automation.model.PersonalVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonalVocabularyRepository extends JpaRepository<PersonalVocabulary, Long> {
    List<PersonalVocabulary> findByWordContainingIgnoreCase(String keyword);
}