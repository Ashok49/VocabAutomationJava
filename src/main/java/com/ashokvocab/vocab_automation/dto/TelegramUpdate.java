package com.ashokvocab.vocab_automation.dto;

public class TelegramUpdate {
    private TelegramMessage message;

    public TelegramMessage getMessage() {
        return message;
    }

    public void setMessage(TelegramMessage message) {
        this.message = message;
    }
}