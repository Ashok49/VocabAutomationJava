package com.ashokvocab.vocab_automation.controllers;

import com.ashokvocab.vocab_automation.model.BaseVocabulary;
import com.ashokvocab.vocab_automation.model.MasterVocabulary;
import com.ashokvocab.vocab_automation.service.VocabularySyncService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ashokvocab.vocab_automation.model.VocabularyDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/vocab")
public class VocabController {

    private final VocabularySyncService vocabularySyncService;

    @Autowired
    public VocabController(VocabularySyncService vocabularySyncService) {
        this.vocabularySyncService = vocabularySyncService;
    }

    @GetMapping("/test")
    public String test() {
        return "Hello from Spring Boot !!";
    }

    @PostMapping("/sync")
    public String syncIndividualTables() {
        vocabularySyncService.syncIndividualTablesFromDrive();
        return "Individual tables synced from Drive";
    }

    @PostMapping("/sync-master")
    public String syncMasterTable() {
        vocabularySyncService.syncMasterTableFromIndividualTables();
        return "Master table synced from individual tables";
    }

    @GetMapping("/all")
    public List<MasterVocabulary> getAllWords() {
        return vocabularySyncService.getAllWords();
    }

    @GetMapping("/table/{table}")
    public List<VocabularyDTO> getVocabularyByTable(@PathVariable String table) {
        return vocabularySyncService.getVocabularyByTable(table.toLowerCase());
    }
}
