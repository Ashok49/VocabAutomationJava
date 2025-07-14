// src/main/java/com/ashokvocab/vocab_automation/service/impl/RacingVocabularyServcieImpl.java
package com.ashokvocab.vocab_automation.service.impl;

import com.ashokvocab.vocab_automation.model.RacingVocabulary;
import com.ashokvocab.vocab_automation.repository.RacingVocabularyRepository;
import com.ashokvocab.vocab_automation.service.RacingVocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RacingVocabularyServcieImpl implements RacingVocabularyService {

    @Autowired
    private RacingVocabularyRepository racingVocabularyRepository;

    @Override
    public List<RacingVocabulary> findAll() {
        return racingVocabularyRepository.findAll();
    }

    @Override
    public Optional<RacingVocabulary> findById(Long id) {
        return racingVocabularyRepository.findById(id);
    }

    @Override
    public RacingVocabulary save(RacingVocabulary vocab) {
        return racingVocabularyRepository.save(vocab);
    }

    @Override
    public void deleteById(Long id) {
        racingVocabularyRepository.deleteById(id);
    }

    @Override
    public List<RacingVocabulary> search(String keyword) {
        return racingVocabularyRepository.findByWordContainingIgnoreCase(keyword);
    }

    @Override
    public List<RacingVocabulary> saveAll(List<RacingVocabulary> vocabularies) {
        return racingVocabularyRepository.saveAll(vocabularies);
    }

    @Override
    public Page<RacingVocabulary> findAll(Pageable pageable) {
        return racingVocabularyRepository.findAll(pageable);
    }
}
