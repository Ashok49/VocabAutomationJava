package com.ashokvocab.vocab_automation.service.impl;

    import com.ashokvocab.vocab_automation.exception.VocabAutomationException;
    import com.ashokvocab.vocab_automation.model.GoogleDriveFile;
    import com.ashokvocab.vocab_automation.service.GoogleDriveService;
    import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
    import com.google.api.client.json.jackson2.JacksonFactory;
    import com.google.api.services.drive.Drive;
    import com.google.api.services.drive.model.File;
    import com.google.api.services.drive.model.FileList;
    import com.google.auth.http.HttpCredentialsAdapter;
    import com.google.auth.oauth2.GoogleCredentials;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;

    import jakarta.annotation.PostConstruct;
    import java.io.FileInputStream;
    import java.io.InputStream;
    import java.util.ArrayList;
    import java.util.List;

    @Service
    public class GoogleDriveServiceImpl implements GoogleDriveService {

        private static final Logger logger = LoggerFactory.getLogger(GoogleDriveServiceImpl.class);

        @Value("${google.key.path}")
        private String credentialPath;

        @Value("${google.folder.id}")
        private String folderId;

        private Drive driveService;

        @PostConstruct
        public void init() {
            try {
                InputStream in = new FileInputStream(credentialPath);
                //InputStream in = getClass().getClassLoader().getResourceAsStream(credentialPath);
                GoogleCredentials credentials = GoogleCredentials.fromStream(in)
                        .createScoped(List.of("https://www.googleapis.com/auth/drive.readonly"));
                driveService = new Drive.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JacksonFactory.getDefaultInstance(),
                        new HttpCredentialsAdapter(credentials)
                ).setApplicationName("VocabAutomation").build();
                logger.info("Google drive service connected...");
            } catch (Exception e) {
                logger.error("Failed to initialize Google Drive service: {}", e.getMessage(), e);
                throw new VocabAutomationException("Failed to initialize Google Drive service", e);
            }
        }

        @Override
        public List<GoogleDriveFile> getDocFiles() {
            List<GoogleDriveFile> files = new ArrayList<>();
            try {
                String query = String.format("'%s' in parents and trashed = false and mimeType = 'application/vnd.google-apps.document'", folderId);
                Drive.Files.List request = driveService.files().list()
                        .setQ(query)
                        .setFields("files(id, name)");
                FileList result = request.execute();
                for (File file : result.getFiles()) {
                    files.add(new GoogleDriveFile(file.getId(), file.getName()));
                }
            } catch (Exception e) {
                logger.error("Error fetching Google Docs: {}", e.getMessage(), e);
                throw new VocabAutomationException("Error fetching Google Docs", e);
            }
            return files;
        }

        @Override
        public byte[] downloadDocx(String fileId) {
            try (java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream()) {
                driveService.files().export(fileId, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                        .executeMediaAndDownloadTo(outputStream);
                return outputStream.toByteArray();
            } catch (Exception e) {
                logger.error("Failed to download DOCX for fileId {}: {}", fileId, e.getMessage(), e);
                throw new VocabAutomationException("Failed to download DOCX for fileId " + fileId, e);
            }
        }
    }