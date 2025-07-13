package com.ashokvocab.vocab_automation.repository;

import com.ashokvocab.vocab_automation.model.DailyVocabBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyVocabBatchRepository extends JpaRepository<DailyVocabBatch, Long> {
    Optional<DailyVocabBatch> findByTableNameAndRunDate(String tableName, LocalDate runDate);
    Optional<DailyVocabBatch> findFirstByTableNameOrderByRunDateDesc(String tableName);
}