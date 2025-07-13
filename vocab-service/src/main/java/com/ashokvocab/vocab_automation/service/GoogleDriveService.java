// src/main/java/com/ashokvocab/vocab_automation/service/GoogleDriveService.java
package com.ashokvocab.vocab_automation.service;

import com.ashokvocab.vocab_automation.model.GoogleDriveFile;

import java.util.List;

public interface GoogleDriveService {
    List<GoogleDriveFile> getDocFiles();
    byte[] downloadDocx(String fileId);
}