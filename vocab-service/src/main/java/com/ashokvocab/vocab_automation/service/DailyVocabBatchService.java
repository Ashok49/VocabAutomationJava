package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.model.DailyVocabBatch;

import java.util.List;
import java.util.Optional;

public interface DailyVocabBatchService {
    List<DailyVocabBatch> findAll();
    Optional<DailyVocabBatch> findById(Long id);
    DailyVocabBatch save(DailyVocabBatch batch);
    void deleteById(Long id);
}