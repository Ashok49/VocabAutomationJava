package com.ashokvocab.vocab_automation.service.impl;

import com.ashokvocab.vocab_automation.dto.PaginatedVocabularyResponse;
import com.ashokvocab.vocab_automation.dto.TodayVocabularyResponse;
import com.ashokvocab.vocab_automation.dto.VocabularyDTO;
import com.ashokvocab.vocab_automation.repository.DailyVocabBatchRepository;
import com.ashokvocab.vocab_automation.repository.MasterVocabularyRepository;
import com.ashokvocab.vocab_automation.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import com.ashokvocab.vocab_automation.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import com.ashokvocab.vocab_automation.util.PdfUtil;
import com.ashokvocab.vocab_automation.kafka.producer.KafkaJsonProducer;
import com.ashokvocab.vocab_automation.dto.VocabBatchReadyDTO;
import com.ashokvocab.vocab_automation.repository.UserRepository;

@Service
public class VocabularySyncServiceImpl implements VocabularySyncService {

    private final SoftwareVocabularyService softwareService;
    private final StockmarketVocabularyService stockmarketService;
    private final TravelVocabularyService travelService;
    private final GoogleDriveService googleDriveService;
    private final RacingVocabularyService racingService;
    private final PodcastVocabularyService podcastService;
    private final PersonalVocabularyService personalService;
    private final GeneralVocabularyService generalService;
    private final S3Service s3Service;
    private final KafkaJsonProducer kafkaJsonProducer;

    private static final Logger logger = LoggerFactory.getLogger(VocabularySyncServiceImpl.class);

    @Autowired
    private MasterVocabularyRepository masterVocabularyRepository;
    @Autowired
    private DailyVocabBatchRepository batchRepository;

    @Autowired
    private UserRepository userRepository;



    public VocabularySyncServiceImpl(
            SoftwareVocabularyService softwareService,
            StockmarketVocabularyService stockmarketService,
            TravelVocabularyService travelService,
            GoogleDriveService googleDriveService,
            RacingVocabularyService racingService,
            PodcastVocabularyService podcastService,
            PersonalVocabularyService personalService,
            GeneralVocabularyService generalService,
            S3Service s3Service,
            KafkaJsonProducer kafkaJsonProducer
    ) {
        this.softwareService = softwareService;
        this.stockmarketService = stockmarketService;
        this.travelService = travelService;
        this.googleDriveService = googleDriveService;
        this.racingService = racingService;
        this.podcastService = podcastService;
        this.personalService = personalService;
        this.generalService = generalService;
        this.s3Service = s3Service;
        this.kafkaJsonProducer = kafkaJsonProducer;
    }

@Override
public void syncIndividualTablesFromDrive() {
    var files = googleDriveService.getDocFiles();

    for (var file : files) {
        var inputStream = googleDriveService.downloadDocx(file.getId());
        var vocabularies = parseVocabFile(inputStream);

        if (file.getName().toLowerCase().contains("software")) {
            List<SoftwareVocabulary> existing = softwareService.findAll();
            Set<String> existingWords = existing.stream()
                .map(SoftwareVocabulary::getWord)
                .collect(Collectors.toSet());
            List<SoftwareVocabulary> newWords = vocabularies.stream()
                .map(v -> new SoftwareVocabulary(v.getWord(), v.getMeaning()))
                .filter(v -> !existingWords.contains(v.getWord()))
                .collect(Collectors.toList());
            logger.info("Inserting Software words: {}", newWords.stream().map(SoftwareVocabulary::getWord).collect(Collectors.toList()));
            softwareService.saveAll(newWords);

        } else if (file.getName().toLowerCase().contains("stockmarket")) {
            List<StockmarketVocabulary> existing = stockmarketService.findAll();
            Set<String> existingWords = existing.stream()
                .map(StockmarketVocabulary::getWord)
                .collect(Collectors.toSet());
            List<StockmarketVocabulary> newWords = vocabularies.stream()
                .map(v -> new StockmarketVocabulary(v.getWord(), v.getMeaning()))
                .filter(v -> !existingWords.contains(v.getWord()))
                .collect(Collectors.toList());
            logger.info("Inserting Stockmarket words: {}", newWords.stream().map(StockmarketVocabulary::getWord).collect(Collectors.toList()));
            stockmarketService.saveAll(newWords);

        } else if (file.getName().toLowerCase().contains("travel")) {
            List<TravelVocabulary> existing = travelService.findAll();
            Set<String> existingWords = existing.stream()
                .map(TravelVocabulary::getWord)
                .collect(Collectors.toSet());
            List<TravelVocabulary> newWords = vocabularies.stream()
                .map(v -> new TravelVocabulary(v.getWord(), v.getMeaning()))
                .filter(v -> !existingWords.contains(v.getWord()))
                .collect(Collectors.toList());
            logger.info("Inserting Travel words: {}", newWords.stream().map(TravelVocabulary::getWord).collect(Collectors.toList()));
            travelService.saveAll(newWords);

        } else if (file.getName().toLowerCase().contains("racing")) {
            List<RacingVocabulary> existing = racingService.findAll();
            Set<String> existingWords = existing.stream()
                .map(RacingVocabulary::getWord)
                .collect(Collectors.toSet());
            List<RacingVocabulary> newWords = vocabularies.stream()
                .map(v -> new RacingVocabulary(v.getWord(), v.getMeaning()))
                .filter(v -> !existingWords.contains(v.getWord()))
                .collect(Collectors.toList());
            logger.info("Inserting Racing words: {}", newWords.stream().map(RacingVocabulary::getWord).collect(Collectors.toList()));
            racingService.saveAll(newWords);

        } else if (file.getName().toLowerCase().contains("podcast")) {
            List<PodcastVocabulary> existing = podcastService.findAll();
            Set<String> existingWords = existing.stream()
                .map(PodcastVocabulary::getWord)
                .collect(Collectors.toSet());
            List<PodcastVocabulary> newWords = vocabularies.stream()
                .map(v -> new PodcastVocabulary(v.getWord(), v.getMeaning()))
                .filter(v -> !existingWords.contains(v.getWord()))
                .collect(Collectors.toList());
            logger.info("Inserting Podcast words: {}", newWords.stream().map(PodcastVocabulary::getWord).collect(Collectors.toList()));
            podcastService.saveAll(newWords);

        } else if (file.getName().toLowerCase().contains("personal")) {
            List<PersonalVocabulary> existing = personalService.findAll();
            Set<String> existingWords = existing.stream()
                .map(PersonalVocabulary::getWord)
                .collect(Collectors.toSet());
            List<PersonalVocabulary> newWords = vocabularies.stream()
                .map(v -> new PersonalVocabulary(v.getWord(), v.getMeaning()))
                .filter(v -> !existingWords.contains(v.getWord()))
                .collect(Collectors.toList());
            logger.info("Inserting Personal words: {}", newWords.stream().map(PersonalVocabulary::getWord).collect(Collectors.toList()));
            personalService.saveAll(newWords);

        } else if (file.getName().toLowerCase().contains("general")) {
            List<GeneralVocabulary> existing = generalService.findAll();
            Set<String> existingWords = existing.stream()
                .map(GeneralVocabulary::getWord)
                .collect(Collectors.toSet());
            List<GeneralVocabulary> newWords = vocabularies.stream()
                .map(v -> new GeneralVocabulary(v.getWord(), v.getMeaning()))
                .filter(v -> !existingWords.contains(v.getWord()))
                .collect(Collectors.toList());
            logger.info("Inserting General words: {}", newWords.stream().map(GeneralVocabulary::getWord).collect(Collectors.toList()));
            generalService.saveAll(newWords);
        }
    }
}

        public List<Vocabulary> parseVocabFile(byte[] docxBytes) {
            List<Vocabulary> wordMeanings = new ArrayList<>();
            try (XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(docxBytes))) {
                List<String> lines = doc.getParagraphs().stream()
                    .map(p -> p.getText().trim())
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toList());

                for (String line : lines) {
                    String cleanLine = line.trim();
                    if (cleanLine.isEmpty()) continue;
                    if (!cleanLine.contains(":")) continue;
                    if (cleanLine.chars().filter(ch -> ch == '-').count() > 5) continue;

                    String[] parts = cleanLine.split(":", 2);
                    if (parts.length < 2) continue;
                    String word = parts[0].trim();
                    String meaning = parts[1].trim();

                    if (!word.isEmpty() && !meaning.isEmpty()) {
                        wordMeanings.add(new Vocabulary(word, meaning));
                    }
                }

                // Remove duplicates, keep last meaning for each word
                Map<String, Vocabulary> map = new LinkedHashMap<>();
                for (Vocabulary v : wordMeanings) {
                    map.put(v.getWord().toLowerCase(), v);
                }
                wordMeanings = new ArrayList<>(map.values());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return wordMeanings;
        }

        @Override
        public void syncMasterTableFromIndividualTables() {
            List<MasterVocabulary> allMasterVocabularies = new ArrayList<>();

            softwareService.findAll().forEach(v ->
                allMasterVocabularies.add(new MasterVocabulary(v.getWord(), v.getMeaning(), "software"))
            );
            stockmarketService.findAll().forEach(v ->
                allMasterVocabularies.add(new MasterVocabulary(v.getWord(), v.getMeaning(), "stockmarket"))
            );
            travelService.findAll().forEach(v ->
                allMasterVocabularies.add(new MasterVocabulary(v.getWord(), v.getMeaning(), "travel"))
            );
            racingService.findAll().forEach(v ->
                allMasterVocabularies.add(new MasterVocabulary(v.getWord(), v.getMeaning(), "racing"))
            );
            podcastService.findAll().forEach(v ->
                allMasterVocabularies.add(new MasterVocabulary(v.getWord(), v.getMeaning(), "podcast"))
            );
            personalService.findAll().forEach(v ->
                allMasterVocabularies.add(new MasterVocabulary(v.getWord(), v.getMeaning(), "personal"))
            );
            generalService.findAll().forEach(v ->
                allMasterVocabularies.add(new MasterVocabulary(v.getWord(), v.getMeaning(), "general"))
            );

            saveOrUpdateWords(allMasterVocabularies);
        }


        @Override
        public void saveOrUpdateWords(List<MasterVocabulary> vocabularies) {
            Map<String, MasterVocabulary> uniqueWords = new LinkedHashMap<>();
            for (MasterVocabulary vocab : vocabularies) {
                String wordLower = vocab.getWord().toLowerCase();
                String meaningLower = vocab.getMeaning() != null ? vocab.getMeaning().toLowerCase() : null;
                MasterVocabulary lowerVocab = new MasterVocabulary(wordLower, meaningLower, vocab.getSourceTable());
                uniqueWords.put(wordLower, lowerVocab); // last occurrence wins
            }
            List<MasterVocabulary> toSave = new ArrayList<>();
            for (MasterVocabulary vocab : uniqueWords.values()) {
                Optional<MasterVocabulary> existing = masterVocabularyRepository.findByWord(vocab.getWord());
                MasterVocabulary entity = existing.orElse(new MasterVocabulary());
                entity.setWord(vocab.getWord());
                entity.setMeaning(vocab.getMeaning());
                entity.setSourceTable(vocab.getSourceTable());
                entity.setCreatedDate(LocalDateTime.now());
                toSave.add(entity);
            }
            masterVocabularyRepository.saveAll(toSave);
        }


        @Override
        public List<MasterVocabulary> getAllWords() {
            return masterVocabularyRepository.findAll();
        }

        @Override
        public TodayVocabularyResponse getTodayVocabulary() {
            String tableName = "software_vocabulary";
            Optional<DailyVocabBatch> lastBatch = batchRepository.findFirstByTableNameOrderByRunDateDesc(tableName);

            List<VocabularyDTO> vocabulary;
            Map<String, String> stories = new HashMap<>();
            String audioUrl = "";
            String pdfUrl = "";

            if (lastBatch.isPresent() && lastBatch.get().getRunDate().isEqual(LocalDate.now())) {

                int offset = Math.max(0, lastBatch.get().getOffset().intValue() - 10);
                vocabulary = getVocabularyByTable(tableName, offset, 10);

                pdfUrl = lastBatch.get().getPdfUrl();
                audioUrl = lastBatch.get().getAudioUrl();

                // Download PDF from S3 and extract stories
                byte[] pdfBytes = s3Service.downloadFile(pdfUrl);

                String pdfText = PdfUtil.extractText(pdfBytes);
                stories.put("generalStory", PdfUtil.extractSection(pdfText, "General Story"));
                stories.put("softwareStory", PdfUtil.extractSection(pdfText, "Software Story"));
            } else {
                vocabulary = getVocabularyByTable(tableName, 0, 10);
            }

            return new TodayVocabularyResponse(vocabulary, stories, audioUrl, pdfUrl);
        }


        @Override
        public PaginatedVocabularyResponse getAllWordsPaginated(int page, int size) {
            Pageable pageable = PageRequest.of(page, size);
            Page<MasterVocabulary> vocabPage = masterVocabularyRepository.findAll(pageable);

            PaginatedVocabularyResponse response = new PaginatedVocabularyResponse();
            response.setContent(
                vocabPage.getContent().stream()
                    .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                    .collect(Collectors.toList())
            );
            response.setPage(vocabPage.getNumber());
            response.setTotalPages(vocabPage.getTotalPages());
            response.setTotalElements(vocabPage.getTotalElements());
            return response;
        }

        @Override
        public List<VocabularyDTO> getVocabularyByTable(String table) {
            switch (table) {
                case "software":
                    return softwareService.findAll().stream()
                        .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()) {
                        })
                        .collect(Collectors.toList());
                case "stockmarket":
                    return stockmarketService.findAll().stream()
                        .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                        .collect(Collectors.toList());
                case "travel":
                    return travelService.findAll().stream()
                        .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                        .collect(Collectors.toList());
                case "racing":
                    return racingService.findAll().stream()
                        .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                        .collect(Collectors.toList());
                case "podcast":
                    return podcastService.findAll().stream()
                        .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                        .collect(Collectors.toList());
                case "personal":
                    return personalService.findAll().stream()
                        .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                        .collect(Collectors.toList());
                case "general":
                    return generalService.findAll().stream()
                        .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                        .collect(Collectors.toList());
                case "all":
                    return masterVocabularyRepository.findAll().stream()
                        .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                        .collect(Collectors.toList());
                default:
                    throw new IllegalArgumentException("Unknown table: " + table);
            }
        }

       @Override
        public List<VocabularyDTO> getVocabularyByTable(String table, int offset, int limit) {
            Pageable pageable = PageRequest.of(offset/limit, limit);
            List<VocabularyDTO> result = switch (table) {
                case "software_vocabulary" -> softwareService.findAll(pageable)
                    .stream()
                    .map(v -> (SoftwareVocabulary) v)
                    .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                    .collect(Collectors.toList());

                case "stockmarket_vocabulary" -> stockmarketService.findAll(pageable)
                    .stream()
                    .map(v -> (StockmarketVocabulary) v)
                    .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                    .collect(Collectors.toList());

                case "travel_vocabulary" -> travelService.findAll(pageable)
                    .stream()
                    .map(v -> (TravelVocabulary) v)
                    .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                    .collect(Collectors.toList());

                case "racing_vocabulary" -> racingService.findAll(pageable)
                    .stream()
                    .map(v -> (RacingVocabulary) v)
                    .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                    .collect(Collectors.toList());

                case "podcast_vocabulary" -> podcastService.findAll(pageable)
                    .stream()
                    .map(v -> (PodcastVocabulary) v)
                    .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                    .collect(Collectors.toList());

                case "personal_vocabulary" -> personalService.findAll(pageable)
                    .stream()
                    .map(v -> (PersonalVocabulary) v)
                    .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                    .collect(Collectors.toList());

                case "general_vocabulary" -> generalService.findAll(pageable)
                    .stream()
                    .map(v -> (GeneralVocabulary) v)
                    .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                    .collect(Collectors.toList());

                case "master_vocabulary" -> masterVocabularyRepository.findAll(pageable)
                    .stream()
                    .map(v -> (MasterVocabulary) v)
                    .map(v -> new VocabularyDTO(v.getWord(), v.getMeaning()))
                    .collect(Collectors.toList());

                default -> throw new IllegalArgumentException("Unknown table: " + table);
            };

            return result;
        }

    /**
     * Sends today's vocabulary batch to Kafka if present in the run table.
     * @param tableName the vocabulary table name
     * @return true if batch was sent, false otherwise
     */
// In VocabularySyncServiceImpl.java

public boolean sendTodayBatchToKafka(String tableName) {
    Optional<DailyVocabBatch> lastBatch = batchRepository.findFirstByTableNameOrderByRunDateDesc(tableName);
    if (lastBatch.isPresent() && lastBatch.get().getRunDate().isEqual(LocalDate.now())) {
        String audioUrl = lastBatch.get().getAudioUrl();
        String pdfUrl = lastBatch.get().getPdfUrl();
        Object principal = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = principal instanceof String ? (String) principal : null;

        Optional<User> userOpt = userRepository.findByUserid(userId);
        User user = userOpt.orElse(null);
        String email = user != null ? user.getEmail() : null;
        String phone = user != null ? user.getPhone() : null;

        VocabBatchReadyDTO dto = new VocabBatchReadyDTO(audioUrl, pdfUrl, userId, email, phone);
        kafkaJsonProducer.sendJson("vocab.batch.ready", dto);
        logger.info("Sent audioUrl, pdfUrl, and userId to Kafka for table: {}", tableName);
        return true;
    }
    logger.info("No batch found for today in table: {}", tableName);
    return false;
}

    //Get 10 words given a table name.. find the offset value frm prev day...
    //Check if the words are already delivered today.. if delivered check the run table (SELECT * FROM public.daily_vocab_batches
    //Integrate with open ai to get stories for the words
    //Integrate with open to get audio for the stories generated
    //Save both pds and audio to s3 seperate buckets
    //save the run to the table..
    //Send email with pdf
    //Trigger twilio call with audio


    }