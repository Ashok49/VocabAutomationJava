package com.ashokvocab.vocab_automation.service.impl;

    import com.ashokvocab.vocab_automation.exception.VocabAutomationException;
    import com.ashokvocab.vocab_automation.service.OpenAIService;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.http.*;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.HttpStatusCodeException;
    import org.springframework.web.client.RestTemplate;
    import com.ashokvocab.vocab_automation.dto.VocabularyDTO;
    import java.util.stream.Collectors;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @Service
    public class OpenAIServiceImpl implements OpenAIService {

        private final String apiKey;
        private final String model;
        private final RestTemplate restTemplate = new RestTemplate();
        private static final Logger logger = LoggerFactory.getLogger(OpenAIServiceImpl.class);

        public OpenAIServiceImpl(
                @Value("${openai.api.key}") String apiKey,
                @Value("${openai.api.model}") String model) {
            this.apiKey = apiKey;
            this.model = model;
        }

        @Override
        public String generateStory(List<VocabularyDTO> vocab, String context) {
            String wordList = vocab.stream()
                    .map(VocabularyDTO::getWord)
                    .collect(Collectors.joining(", "));

            String prompt = context.equals("general")
                    ? String.format("Write a simple and creative general story using the following words:: %s. Keep it under 150 words.", wordList)
                    : String.format("Write a simple and creative software story on either redis, kafka or postgres or react using the following words: %s. Keep it under 150 words.", wordList);

            return callOpenAIApi(prompt);
        }

        private String callOpenAIApi(String prompt) {

            long startTime = System.currentTimeMillis();
            logger.info("Starting OpenAI API call with prompt length: {} characters", prompt.length());
            logger.info("Using OpenAI model: {}", model);

            try {
                String url = "https://api.openai.com/v1/chat/completions";
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(apiKey);
                headers.setContentType(MediaType.APPLICATION_JSON);

                Map<String, Object> message = new HashMap<>();
                message.put("role", "user");
                message.put("content", prompt);

                Map<String, Object> body = new HashMap<>();
                body.put("model", model);
                body.put("messages", List.of(message));

                logger.info("Using OpenAI model: {}", model);

                HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
                ResponseEntity<Map> response = restTemplate.exchange(
                        url, HttpMethod.POST, request, Map.class
                );

                if (response.getBody() == null) {
                    throw new VocabAutomationException("Empty response from OpenAI API");
                }

                long duration = System.currentTimeMillis() - startTime;
                logger.info("Successfully received response from OpenAI API. Time taken: {} ms", duration);

                Map choices = ((List<Map>) response.getBody().get("choices")).get(0);
                return ((Map) choices.get("message")).get("content").toString();
            } catch (HttpStatusCodeException e) {
                throw new VocabAutomationException("OpenAI API error: " + e.getResponseBodyAsString(), e);
            } catch (Exception e) {
                throw new VocabAutomationException("Failed to generate story: " + e.getMessage(), e);
            }
        }

        @Override
        public byte[] textToAudio(String text, String voice) {

            long startTime = System.currentTimeMillis();
            logger.info("Starting text-to-speech API call for text length: {} characters", text.length());
            logger.info("Using voice: {}", voice);

            try {
                String url = "https://api.openai.com/v1/audio/speech";
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(apiKey);
                headers.setContentType(MediaType.APPLICATION_JSON);

                Map<String, Object> body = new HashMap<>();
                body.put("model", "tts-1");
                body.put("input", text);
                body.put("voice", voice);

                HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
                ResponseEntity<byte[]> response = restTemplate.exchange(
                    url, HttpMethod.POST, request, byte[].class
                );

                if (response.getBody() == null) {
                    throw new VocabAutomationException("Empty audio response from OpenAI API");
                }

                long duration = System.currentTimeMillis() - startTime;
                logger.info("Successfully received audio response. Time taken: {} ms, Audio size: {} bytes",
                        duration, response.getBody().length);

                return response.getBody();
            } catch (HttpStatusCodeException e) {
                throw new VocabAutomationException("OpenAI API error: " + e.getResponseBodyAsString(), e);
            } catch (Exception e) {
                throw new VocabAutomationException("Failed to generate audio: " + e.getMessage(), e);
            }
        }
    }