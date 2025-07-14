package com.ashokvocab.vocab_automation.dto;

import java.util.List;

public class PaginatedVocabularyResponse {
    private List<VocabularyDTO> content;
    private int page;
    private int totalPages;
    private long totalElements;

    public List<VocabularyDTO> getContent() {
        return content;
    }

    public void setContent(List<VocabularyDTO> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}