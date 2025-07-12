package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.dto.PaginatedVocabularyResponse;
import com.ashokvocab.vocab_automation.dto.TodayVocabularyResponse;
import com.ashokvocab.vocab_automation.dto.VocabularyDTO;
import com.ashokvocab.vocab_automation.model.MasterVocabulary;

import java.util.List;

public interface VocabularySyncService {
    void syncIndividualTablesFromDrive();
    void syncMasterTableFromIndividualTables();
    void saveOrUpdateWords(List<MasterVocabulary> vocabularies);

    List<MasterVocabulary> getAllWords();
    //add get all paginated method
    PaginatedVocabularyResponse getAllWordsPaginated(int page, int limit);

    List<VocabularyDTO> getVocabularyByTable(String table);

    TodayVocabularyResponse getTodayVocabulary();

    List<VocabularyDTO> getVocabularyByTable(String table, int offset, int limit);

    }
