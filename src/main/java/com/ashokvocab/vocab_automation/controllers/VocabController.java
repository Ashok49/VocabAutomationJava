package com.ashokvocab.vocab_automation.controllers;

import com.ashokvocab.vocab_automation.service.VocabularySyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public String syncAll() {
        //CompletableFuture<Void> future = CompletableFuture.runAsync(vocabularySyncService::syncAllVocabularies);
        //future.join();
        vocabularySyncService.syncAllVocabularies();
        return "All vocabularies synced";
    }
}