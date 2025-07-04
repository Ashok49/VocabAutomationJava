package com.ashokvocab.vocab_automation.controllers;

import com.ashokvocab.vocab_automation.dto.TelegramUpdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/telegram")
public class TelegramController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${TELEGRAM_BOT_TOKEN}")
    private String telegramBotToken;

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(@RequestBody TelegramUpdate update, HttpServletRequest request) {
        var message = update.getMessage();
        String baseUrl = request.getScheme() + "://" + request.getServerName() +
                ((request.getServerPort() == 80 || request.getServerPort() == 443) ? "" : ":" + request.getServerPort());
        if (message == null || message.getChat() == null || message.getText() == null) {
            return ResponseEntity.ok().build();
        }

        Long chatId = message.getChat().getId();
        String messageText = message.getText().trim();
        String responseText;

        if ("/sync".equalsIgnoreCase(messageText)) {
            triggerInternalApi(baseUrl + "/api/vocab/sync", HttpMethod.POST);
            responseText = "✅ Sync triggered.";
        } else if (messageText.toLowerCase().startsWith("/batch")) {
            String[] parts = messageText.split("\\s+");
            String tableName = parts.length >= 2 ? parts[1] : "general_vocabulary";
            triggerInternalApi(baseUrl + "/api/vocab/send-batch/" + tableName, HttpMethod.POST);
            responseText = "✅ Batch sent for `" + tableName + "`.";
        } else {
            responseText = "🤖 Welcome! Use:\n\n/sync\n/batch [table_name]";
        }

        sendTelegramMessage(chatId, responseText);
        return ResponseEntity.ok().build();
    }

    private void triggerInternalApi(String url, HttpMethod method) {
        HttpEntity<Void> request = new HttpEntity<>(null);
        restTemplate.exchange(url, method, request, String.class);
    }

    private void sendTelegramMessage(Long chatId, String text) {
        String url = String.format("https://api.telegram.org/bot%s/sendMessage", telegramBotToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var payload = new java.util.HashMap<String, Object>();
        payload.put("chat_id", chatId);
        payload.put("text", text);
        payload.put("parse_mode", "Markdown");

        HttpEntity<Object> request = new HttpEntity<>(payload, headers);
        restTemplate.postForEntity(url, request, String.class);
    }
}