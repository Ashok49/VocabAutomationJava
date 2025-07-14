// src/main/java/com/ashokvocab/vocab_automation/service/impl/PodcastVocabularyServiceImpl.java
package com.ashokvocab.vocab_automation.service.impl;

import com.ashokvocab.vocab_automation.model.PodcastVocabulary;
import com.ashokvocab.vocab_automation.repository.PodcastVocabularyRepository;
import com.ashokvocab.vocab_automation.service.PodcastVocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PodcastVocabularyServiceImpl implements PodcastVocabularyService {

    @Autowired
    private PodcastVocabularyRepository podcastVocabularyRepository;

    @Override
    public List<PodcastVocabulary> findAll() {
        return podcastVocabularyRepository.findAll();
    }

    @Override
    public Optional<PodcastVocabulary> findById(Long id) {
        return podcastVocabularyRepository.findById(id);
    }

    @Override
    public PodcastVocabulary save(PodcastVocabulary vocab) {
        return podcastVocabularyRepository.save(vocab);
    }

    @Override
    public void deleteById(Long id) {
        podcastVocabularyRepository.deleteById(id);
    }

    @Override
    public List<PodcastVocabulary> search(String keyword) {
        return podcastVocabularyRepository.findByWordContainingIgnoreCase(keyword);
    }

    @Override
    public List<PodcastVocabulary> saveAll(List<PodcastVocabulary> vocabularies) {
        return podcastVocabularyRepository.saveAll(vocabularies);
    }

    @Override
    public Page<PodcastVocabulary> findAll(Pageable pageable) {
    return podcastVocabularyRepository.findAll(pageable);
    }
}