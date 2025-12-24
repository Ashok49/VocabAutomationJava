package com.ashokvocab.vocab_automation.service.impl;

import com.ashokvocab.vocab_automation.model.DailyVocabBatch;
import com.ashokvocab.vocab_automation.repository.DailyVocabBatchRepository;
import com.ashokvocab.vocab_automation.service.*;
import com.ashokvocab.vocab_automation.dto.VocabularyDTO;
import com.ashokvocab.vocab_automation.exception.VocabAutomationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VocabularyProcessingServiceImpl implements VocabularyProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(VocabularyProcessingServiceImpl.class);

     private final DailyVocabBatchRepository batchRepository;
     private final EmailService emailService;
     private final ObjectMapper objectMapper;
     private final TwilioService twilioService;
     private final VocabularySyncService vocabularySyncService;
     private final OpenAIService openAIService;
     private final S3Service s3Service;
     private final PdfService pdfService;

     @Autowired
     public VocabularyProcessingServiceImpl(
             DailyVocabBatchRepository batchRepository,
             EmailService emailService,
             ObjectMapper objectMapper,
             TwilioService twilioService,
             VocabularySyncService vocabularySyncService,
             OpenAIService openAIService,
             S3Service s3Service,
             PdfService pdfService) {
         this.batchRepository = batchRepository;
         this.emailService = emailService;
         this.objectMapper = objectMapper;
         this.twilioService = twilioService;
         this.vocabularySyncService = vocabularySyncService;
         this.openAIService = openAIService;
         this.s3Service = s3Service;
         this.pdfService = pdfService;
     }

     @Override
     @Transactional
     public void processVocabularyBatch(String tableName) {

         //Get 10 words given a table name.. find the offset value frm prev day...
         //Check if the words are already delivered today.. if delivered check the run table (SELECT * FROM public.daily_vocab_batches
         //Call open ai to get stories for the words
         //Call open ai to get audio for the stories generated
         //Save both pds and audio to s3 seperate buckets
         //save the run to the table..
         //Send email with pdf
         //Trigger twilio call with audio
         LocalDate today = LocalDate.now();
        logger.info("processVocabularyBatch START - tableName={} today={}", tableName, today);
         Optional<DailyVocabBatch> todaysBatch = batchRepository.findByTableNameAndRunDate(tableName, today);
        logger.info("findByTableNameAndRunDate returned present={} for tableName={} and date={}", todaysBatch.isPresent(), tableName, today);

         try {
             if (todaysBatch.isPresent()) {
                DailyVocabBatch tb = todaysBatch.get();
                logger.info("Processing existing DailyVocabBatch id={} tableName={} runDate={} offset={}", tb.getId(), tb.getTableName(), tb.getRunDate(), tb.getOffset());
                 processExistingBatch(todaysBatch.get());
             } else {
                 // Get last processed batch to determine offset
                 Optional<DailyVocabBatch> lastBatch = batchRepository.findFirstByTableNameOrderByRunDateDesc(tableName);
                logger.info("findFirstByTableNameOrderByRunDateDesc returned present={} for tableName={}", lastBatch.isPresent(), tableName);
                 int offset = 0; // Default offset

                 if (lastBatch.isPresent()) {
                     Long batchOffset = lastBatch.get().getOffset();
                    logger.info("Last batch info id={} runDate={} offset={}", lastBatch.get().getId(), lastBatch.get().getRunDate(), batchOffset);
                     offset = (batchOffset != null) ? batchOffset.intValue() : 0;
                 }

                 // Get 10 words from vocabulary table using offset
                 List<VocabularyDTO> words = vocabularySyncService.getVocabularyByTable(tableName, offset, 10);

                logger.info("Fetched {} words for tableName={} with offset={}", words.size(), tableName, offset);
                 if (words.isEmpty()) {
                     throw new VocabAutomationException("No more words available in vocabulary table");
                 }

                 String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                 String pdfFileName = String.format("vocab_%s.pdf", timestamp);
                 String audioFileName = String.format("audio_%s.mp3", timestamp);

                 // Generate stories using GPT
                 // Generate both general and software stories
                 String generalStory = openAIService.generateStory(words, "general");
                 String softwareStory = openAIService.generateStory(words, "software");

                 byte[] pdfContent = pdfService.generatePdf(words, generalStory, softwareStory);
                 // Create a combined story for processing
                 String textForAudio = buildCombinedTextForAudio(words, generalStory, softwareStory);
                 byte[] audioData = openAIService.textToAudio(textForAudio, "nova");

                 //save audio and pdf to s3 storage
                 String pdfUrl = s3Service.uploadFile(pdfContent, pdfFileName, "application/pdf");
                 String audioUrl = s3Service.uploadFile(audioData, audioFileName, "audio/mpeg");

                 DailyVocabBatch newBatch = new DailyVocabBatch();
                 newBatch.setTableName(tableName);
                 newBatch.setRunDate(today);
                String wordsJson = objectMapper.writeValueAsString(words);
                newBatch.setWordsJson(wordsJson);
                 newBatch.setPdfUrl(pdfUrl);
                 newBatch.setAudioUrl(audioUrl);
                 newBatch.setOffset((long) (offset + words.size())); // Cast to Long since offset is stored as Long

                logger.info("About to save DailyVocabBatch - tableName={} runDate={} offset={} pdfUrlPresent={} audioUrlPresent={} wordsJsonLength= {}", tableName, newBatch.getRunDate(), newBatch.getOffset(), pdfUrl != null && !pdfUrl.isEmpty(), audioUrl != null && !audioUrl.isEmpty(), wordsJson != null ? wordsJson.length() : 0);
                 // Save the batch
                 DailyVocabBatch savedBatch = batchRepository.save(newBatch);
                logger.info("Saved DailyVocabBatch id={} tableName={} runDate={} offset={}", savedBatch.getId(), savedBatch.getTableName(), savedBatch.getRunDate(), savedBatch.getOffset());

                 // Process the newly created batch
                 processExistingBatch(savedBatch);
             }
         } catch (Exception e) {
            logger.error("Failed to processVocabularyBatch tableName={} - error={}", tableName, e.getMessage(), e);
            throw new VocabAutomationException("Failed to process vocabulary batch: " + e.getMessage(), e);
         }
     }

     private void processExistingBatch(DailyVocabBatch batch) throws Exception {
         String pdfUrl = batch.getPdfUrl();
         String audioUrl = batch.getAudioUrl();

        logger.info("processExistingBatch - id={} tableName={} runDate={} pdfUrlPresent={} audioUrlPresent={}", batch.getId(), batch.getTableName(), batch.getRunDate(), pdfUrl != null && !pdfUrl.isEmpty(), audioUrl != null && !audioUrl.isEmpty());
         List<VocabularyDTO> words = objectMapper.readValue(
                 batch.getWordsJson(),
                 new TypeReference<List<VocabularyDTO>>() {}
         );

        logger.info("processExistingBatch - deserialized {} words from batch id={}", words.size(), batch.getId());
         //emailService.sendVocabularyEmail(words, pdfUrl);

         if (audioUrl != null && !audioUrl.isEmpty()) {
             twilioService.makeCall(audioUrl);
         }
     }

     private String buildCombinedTextForAudio(List<VocabularyDTO> words, String generalStory, String softwareStory) {
         StringBuilder builder = new StringBuilder();

         // Add introduction
         builder.append("Here are today's vocabulary words and meanings:\n\n");

         // Add words and meanings
         for (VocabularyDTO word : words) {
             builder.append(word.getWord())
                   .append(": ")
                   .append(word.getMeaning())
                   .append("\n");
         }

         // Add stories
         builder.append("\nHere's a general story:\n")
               .append(generalStory)
               .append("\n\nHere's a software story:\n")
               .append(softwareStory);

         return builder.toString();
     }
 }
