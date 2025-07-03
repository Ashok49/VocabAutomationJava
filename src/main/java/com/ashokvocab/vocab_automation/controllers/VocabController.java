package com.ashokvocab.vocab_automation.controllers;

import com.ashokvocab.vocab_automation.model.MasterVocabulary;
import com.ashokvocab.vocab_automation.service.VocabularyProcessingService;
import com.ashokvocab.vocab_automation.service.VocabularySyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ashokvocab.vocab_automation.dto.VocabularyDTO;
import org.springframework.http.HttpStatus;
import java.util.List;

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

    @GetMapping("/table/{table}")
    public List<VocabularyDTO> getVocabularyByTable(@PathVariable String table) {
        return vocabularySyncService.getVocabularyByTable(table.toLowerCase());
    }

@PostMapping("/process/{table}")
public ResponseEntity<?> processVocabularyBatch(@PathVariable String table) {
    try {
        vocabularyProcessingService.processVocabularyBatch(table.toLowerCase());
        return ResponseEntity.ok("Vocabulary batch processed for table: " + table);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error processing batch: " + e.getMessage());
    }
}

    //Get 10 words given a table name.. find the offset value frm prev day...
    //Check if the words are already delivered today.. if delivered check the run table (SELECT * FROM public.daily_vocab_batches
    //Integrate with open ai to get stories for the words
    //Integrate with open to get audio for the stories generated
    //Save both pds and audio to s3 seperate buckets
    //save the run to the table..
    //Send email with pdf
    //Trigger twilio call with audio

}
