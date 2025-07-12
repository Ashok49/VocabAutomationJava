package com.ashokvocab.vocab_automation.controllers;

import com.ashokvocab.vocab_automation.dto.TodayVocabularyResponse;
import com.ashokvocab.vocab_automation.model.MasterVocabulary;
import com.ashokvocab.vocab_automation.service.VocabularyProcessingService;
import com.ashokvocab.vocab_automation.service.VocabularySyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ashokvocab.vocab_automation.dto.VocabularyDTO;
import org.springframework.http.HttpStatus;
import java.util.List;
import com.ashokvocab.vocab_automation.dto.PaginatedVocabularyResponse;

@RestController
@RequestMapping("/api/vocab")
public class VocabController {

    private final VocabularySyncService vocabularySyncService;
    private final VocabularyProcessingService vocabularyProcessingService;
    @Autowired
    public VocabController(VocabularySyncService vocabularySyncService, VocabularyProcessingService vocabularyProcessingService) {
        this.vocabularySyncService = vocabularySyncService;
        this.vocabularyProcessingService = vocabularyProcessingService;
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

    @GetMapping("/all-paginated")
    public ResponseEntity<PaginatedVocabularyResponse> getAllWordsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            PaginatedVocabularyResponse response = vocabularySyncService.getAllWordsPaginated(page, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }


    @GetMapping("/today")
    public ResponseEntity<?> getTodaysVocabulary() {
        try {
            TodayVocabularyResponse todaysVocabulary = vocabularySyncService.getTodayVocabulary();
            return ResponseEntity.ok(todaysVocabulary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error fetching today's vocabulary: " + e.getMessage());
        }
    }

    @GetMapping("/category/{name}")
    public List<VocabularyDTO> getVocabularyByTable(@PathVariable String name) {
        return vocabularySyncService.getVocabularyByTable(name.toLowerCase());
    }

@PostMapping("/send-batch/{table}")
public ResponseEntity<?> processVocabularyBatch(@PathVariable String table) {
    try {
        vocabularyProcessingService.processVocabularyBatch(table.toLowerCase());
        return ResponseEntity.ok("Vocabulary batch processed for table: " + table);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error processing batch: " + e.getMessage());
    }
}

}
