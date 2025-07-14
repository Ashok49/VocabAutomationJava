package com.ashokvocab.vocab_automation.controllers;

import com.ashokvocab.vocab_automation.service.VocabularySyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final VocabularySyncService vocabularySyncService;

    @Autowired
    public NotificationController(VocabularySyncService vocabularySyncService) {
        this.vocabularySyncService = vocabularySyncService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestParam(required = false) String tableName) {
        if (tableName == null || tableName.isEmpty()) {
            tableName = "software_vocabulary";
        }
        boolean sent = vocabularySyncService.sendTodayBatchToKafka(tableName);
        String message = sent ? "Batch sent to Kafka." : "No batch found for today.";
        String status = sent ? "success" : "error";
        return sent ? ResponseEntity.ok(message) : ResponseEntity.badRequest().body(message);
    }

    @PostMapping("/make-call")
    public ResponseEntity<?> makeCall(@RequestParam(required = false) String tableName) {
        if (tableName == null || tableName.isEmpty()) {
            tableName = "software_vocabulary";
        }
        boolean sent = vocabularySyncService.sendTodayBatchToKafka(tableName);
        String message = sent ? "Batch sent to Kafka for Twilio call." : "No batch found for today.";
        return sent ? ResponseEntity.ok(message) : ResponseEntity.badRequest().body(message);
    }

}
