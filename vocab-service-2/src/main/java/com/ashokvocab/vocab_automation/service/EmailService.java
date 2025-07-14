package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.dto.VocabularyDTO;
import java.util.List;

public interface EmailService {
    /**
     * Sends vocabulary email with HTML content and PDF attachment
     *
     * @param words List of vocabulary words with meanings
     * @param pdfUrl The URL of the PDF file to attach
     */
    void sendVocabularyEmail(List<VocabularyDTO> words, String pdfUrl);
}