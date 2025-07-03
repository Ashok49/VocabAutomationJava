package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.dto.VocabularyDTO;

import java.util.List;

public interface OpenAIService {
        String generateStory(List<VocabularyDTO> vocab, String context);

        byte[] textToAudio(String text, String voice); // Returns audio bytes
}