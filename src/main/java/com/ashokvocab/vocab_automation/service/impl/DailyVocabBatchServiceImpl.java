package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.model.DailyVocabBatch;
import com.ashokvocab.vocab_automation.repository.DailyVocabBatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DailyVocabBatchServiceImpl implements DailyVocabBatchService {

    private final DailyVocabBatchRepository repository;

    @Autowired
    public DailyVocabBatchServiceImpl(DailyVocabBatchRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DailyVocabBatch> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<DailyVocabBatch> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public DailyVocabBatch save(DailyVocabBatch batch) {
        return repository.save(batch);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}