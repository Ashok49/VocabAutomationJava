package com.ashokvocab.vocab_automation.service.impl;

import com.ashokvocab.vocab_automation.exception.VocabAutomationException;
import com.ashokvocab.vocab_automation.model.SoftwareVocabulary;
import com.ashokvocab.vocab_automation.repository.SoftwareVocabularyRepository;
import com.ashokvocab.vocab_automation.service.SoftwareVocabularyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoftwareVocabularyServiceImpl implements SoftwareVocabularyService {

    private final SoftwareVocabularyRepository repository;

    public SoftwareVocabularyServiceImpl(SoftwareVocabularyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SoftwareVocabulary> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<SoftwareVocabulary> findAll(Pageable pageable) {
        try {
            return repository.findAll(pageable);
        } catch (Exception e) {
            throw new VocabAutomationException("Error fetching paginated software vocabularies", e);
        }
    }

    @Override
    public Optional<SoftwareVocabulary> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public SoftwareVocabulary save(SoftwareVocabulary vocabulary) {
        return repository.save(vocabulary);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<SoftwareVocabulary> search(String keyword) {
        return repository.findByWordContainingIgnoreCaseOrMeaningContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public List<SoftwareVocabulary> saveAll(List<SoftwareVocabulary> vocabularies) {
        return repository.saveAll(vocabularies);
    }
}