package com.ashokvocab.vocab_automation.service.impl;

import com.ashokvocab.vocab_automation.model.PersonalVocabulary;
import com.ashokvocab.vocab_automation.repository.PersonalVocabularyRepository;
import com.ashokvocab.vocab_automation.service.PersonalVocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalVocabularyServiceImpl implements PersonalVocabularyService {

    @Autowired
    private PersonalVocabularyRepository personalVocabularyRepository;

    @Override
    public List<PersonalVocabulary> findAll() {
        return personalVocabularyRepository.findAll();
    }

    @Override
    public Page<PersonalVocabulary> findAll(Pageable pageable) {
        try {
            return personalVocabularyRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching paginated personal vocabularies", e);
        }
    }

    @Override
    public Optional<PersonalVocabulary> findById(Long id) {
        return personalVocabularyRepository.findById(id);
    }

    @Override
    public PersonalVocabulary save(PersonalVocabulary vocab) {
        return personalVocabularyRepository.save(vocab);
    }

    @Override
    public void deleteById(Long id) {
        personalVocabularyRepository.deleteById(id);
    }

    @Override
    public List<PersonalVocabulary> search(String keyword) {
        return personalVocabularyRepository.findByWordContainingIgnoreCase(keyword);
    }

    @Override
    public List<PersonalVocabulary> saveAll(List<PersonalVocabulary> vocabularies) {
        return personalVocabularyRepository.saveAll(vocabularies);
    }
}