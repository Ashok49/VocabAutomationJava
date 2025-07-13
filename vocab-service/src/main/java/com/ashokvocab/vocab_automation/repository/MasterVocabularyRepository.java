package com.ashokvocab.vocab_automation.repository;

import com.ashokvocab.vocab_automation.model.MasterVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MasterVocabularyRepository extends JpaRepository<MasterVocabulary, Long> {
    Optional<MasterVocabulary> findByWord(String word);
}