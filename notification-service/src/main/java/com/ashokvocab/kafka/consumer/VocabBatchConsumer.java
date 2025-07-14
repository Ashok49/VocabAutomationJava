package com.ashokvocab.kafka.consumer;

import com.ashokvocab.vocab_automation.dto.VocabBatchReadyDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class VocabBatchConsumer {

    @KafkaListener(topics = "vocab.batch.ready", groupId = "notification-service-group")
    public void consume(VocabBatchReadyDTO message) {
        System.out.println("Received message from Kafka topic: " + message.toString());
        // TODO: Add logic to process the message (e.g., send email, make Twilio call)
    }
}
