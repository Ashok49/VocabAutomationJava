package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.model.MasterVocabulary;

import java.util.List;

public interface VocabularySyncService {
    void syncIndividualTablesFromDrive();
    void syncMasterTableFromIndividualTables();
    void saveOrUpdateWords(List<MasterVocabulary> vocabularies);

    List<MasterVocabulary> getAllWords();
}
