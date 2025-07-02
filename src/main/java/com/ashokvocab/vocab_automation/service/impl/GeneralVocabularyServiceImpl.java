package com.ashokvocab.vocab_automation.service.impl;

import com.ashokvocab.vocab_automation.model.GeneralVocabulary;
import com.ashokvocab.vocab_automation.repository.GeneralVocabularyRepository;
import com.ashokvocab.vocab_automation.service.GeneralVocabularyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneralVocabularyServiceImpl implements GeneralVocabularyService {

    private final GeneralVocabularyRepository generalVocabularyRepository;

    public GeneralVocabularyServiceImpl(GeneralVocabularyRepository generalVocabularyRepository) {
        this.generalVocabularyRepository = generalVocabularyRepository;
    }

    @Override
    public List<GeneralVocabulary> findAll() {
        return generalVocabularyRepository.findAll();
    }

    @Override
    public Optional<GeneralVocabulary> findById(Long id) {
        return generalVocabularyRepository.findById(id);
    }

    @Override
    public GeneralVocabulary save(GeneralVocabulary vocab) {
        return generalVocabularyRepository.save(vocab);
    }

    @Override
    public void deleteById(Long id) {
        generalVocabularyRepository.deleteById(id);
    }

    @Override
    public List<GeneralVocabulary> search(String keyword) {
        return generalVocabularyRepository.findByWordContainingIgnoreCase(keyword);
    }

    @Override
    public List<GeneralVocabulary> saveAll(List<GeneralVocabulary> vocabularies) {
        return generalVocabularyRepository.saveAll(vocabularies);
    }
}