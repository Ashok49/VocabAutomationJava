package com.ashokvocab.vocab_automation.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "daily_vocab_batches")
public class DailyVocabBatch {
    private static final Logger logger = LoggerFactory.getLogger(DailyVocabBatch.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "run_date", nullable = false)
    private LocalDate runDate;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "pdf_url", length = 512)
    private String pdfUrl;

    @Column(name = "audio_url", length = 512)
    private String audioUrl;

    @Column(name = "words_json", columnDefinition = "TEXT")
    private String wordsJson;

    @Column(name = "\"offset\"")
    private Long offset;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRunDate() {
        return runDate;
    }

    public void setRunDate(LocalDate runDate) {
        this.runDate = runDate;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getWordsJson() {
        return wordsJson;
    }

    public void setWordsJson(String wordsJson) {
        this.wordsJson = wordsJson;
    }

    public Long getOffset() {
        return offset;
    }


    public void setOffset(Long offset) {
        this.offset = offset;
    }

    @PrePersist
    public void prePersist() {
        // Ensure runDate is always set before insert and log the entity state to prove the hypothesis
        if (this.runDate == null) {
            this.runDate = LocalDate.now();
            logger.info("@PrePersist set runDate to {} for tableName={}", this.runDate, this.tableName);
        } else {
            logger.info("@PrePersist found existing runDate={} for tableName={}", this.runDate, this.tableName);
        }

        logger.debug("@PrePersist DailyVocabBatch about to persist: id={} tableName={} runDate={} offset={} pdfUrlPresent={} audioUrlPresent={} wordsJsonLength={}",
                this.id, this.tableName, this.runDate, this.offset, this.pdfUrl != null && !this.pdfUrl.isEmpty(), this.audioUrl != null && !this.audioUrl.isEmpty(), this.wordsJson != null ? this.wordsJson.length() : 0);
    }

    private int getLastOffset(DailyVocabBatch batch) {
        Long offset = batch.getOffset();
        return offset != null ? offset.intValue() : 0;
    }
}