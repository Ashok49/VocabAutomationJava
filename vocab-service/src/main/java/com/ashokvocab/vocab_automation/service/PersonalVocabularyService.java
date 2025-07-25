// src/main/java/com/ashokvocab/vocab_automation/service/PersonalVocabularyService.java
package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.model.PersonalVocabulary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonalVocabularyService {
    List<PersonalVocabulary> findAll();
    Page<PersonalVocabulary> findAll(Pageable pageable);
    Optional<PersonalVocabulary> findById(Long id);
    PersonalVocabulary save(PersonalVocabulary vocab);
    void deleteById(Long id);
    List<PersonalVocabulary> search(String keyword);
    List<PersonalVocabulary> saveAll(List<PersonalVocabulary> vocabularies);

}