// src/main/java/com/ashokvocab/vocab_automation/dto/TelegramMessage.java
package com.ashokvocab.vocab_automation.dto;

public class TelegramMessage {
    private TelegramChat chat;
    private String text;

    public TelegramChat getChat() {
        return chat;
    }

    public void setChat(TelegramChat chat) {
        this.chat = chat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
