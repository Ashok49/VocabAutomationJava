package com.ashokvocab.controller;

import com.ashokvocab.dto.BaseSseEventDTO;
import com.ashokvocab.dto.VocabWordEventDTO;
import com.ashokvocab.dto.QuizReadyEventDTO;
import com.ashokvocab.dto.StreakEventDTO;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class SseVocabController {
    private final CopyOnWriteArrayList<BaseSseEventDTO> dynamicEvents = new CopyOnWriteArrayList<>();
    private final AtomicInteger eventIndex = new AtomicInteger(0);

    @KafkaListener(topics = "notification.email.sent", groupId = "notification-service-group")
    public void onEmailSent(String message) {
        // You can parse the message if needed
        dynamicEvents.add(new BaseSseEventDTO("email", message));
    }

    @GetMapping(value = "/api/sse/vocab-events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<BaseSseEventDTO>> streamVocabEvents() {
        List<BaseSseEventDTO> events = Arrays.asList(
            new BaseSseEventDTO("vocab", new VocabWordEventDTO("serendipity", "the occurrence of events by chance in a happy or beneficial way")),
            new BaseSseEventDTO("quiz", new QuizReadyEventDTO("quiz123", "Your quiz is ready!")),
            new BaseSseEventDTO("streak", new StreakEventDTO(4, "Good job! You have a 4 day streak. Keep up the good work.")),
            new BaseSseEventDTO("info", "This is a generic info event.")
        );

        Flux<ServerSentEvent<BaseSseEventDTO>> staticEvents = Flux.interval(Duration.ofSeconds(10))
                .take(events.size())
                .map(i -> ServerSentEvent.builder(events.get(i.intValue()))
                        .event(events.get(i.intValue()).getType())
                        .build()
                );

        Flux<ServerSentEvent<BaseSseEventDTO>> dynamicEventFlux = Flux.create(sink -> {
            // Poll for new dynamic events and emit them
            sink.onRequest(n -> {
                while (!dynamicEvents.isEmpty()) {
                    BaseSseEventDTO event = dynamicEvents.remove(0);
                    sink.next(ServerSentEvent.builder(event)
                            .event(event.getType())
                            .build());
                }
            });
        });

        return Flux.merge(staticEvents, dynamicEventFlux);
    }
}
