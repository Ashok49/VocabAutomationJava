package com.ashokvocab.vocab_automation.exception;

public class VocabAutomationException extends RuntimeException {
    public VocabAutomationException(String message) {
        super(message);
    }

    public VocabAutomationException(String message, Throwable cause) {
        super(message, cause);
    }
}