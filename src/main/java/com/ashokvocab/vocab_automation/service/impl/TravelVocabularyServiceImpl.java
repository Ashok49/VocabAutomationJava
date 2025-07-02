// src/main/java/com/ashokvocab/vocab_automation/service/impl/TravelVocabularyServiceImpl.java
        package com.ashokvocab.vocab_automation.service.impl;

        import com.ashokvocab.vocab_automation.model.TravelVocabulary;
        import com.ashokvocab.vocab_automation.repository.TravelVocabularyRepository;
        import com.ashokvocab.vocab_automation.service.TravelVocabularyService;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Optional;

        @Service
        public class TravelVocabularyServiceImpl implements TravelVocabularyService {
            private final TravelVocabularyRepository repository;

            public TravelVocabularyServiceImpl(TravelVocabularyRepository repository) {
                this.repository = repository;
            }

            @Override
            public List<TravelVocabulary> findAll() {
                return repository.findAll();
            }

            @Override
            public Optional<TravelVocabulary> findById(Long id) {
                return repository.findById(id);
            }

            @Override
            public TravelVocabulary save(TravelVocabulary vocab) {
                return repository.save(vocab);
            }

            @Override
            public void deleteById(Long id) {
                repository.deleteById(id);
            }

            @Override
            public List<TravelVocabulary> search(String keyword) {
                return repository.findByWordContainingIgnoreCase(keyword);
            }

            @Override
            public List<TravelVocabulary> saveAll(List<TravelVocabulary> vocabularies) {
                return repository.saveAll(vocabularies);
            }

        }