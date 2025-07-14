package com.ashokvocab.vocab_automation.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaJsonProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaJsonProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendJson(String topic, Object payload) {
        try {
            kafkaTemplate.send(topic, payload);
        } catch (Exception e) {
            // You can use a logger here if available
            System.err.println("Failed to send message to Kafka topic: " + topic);
            e.printStackTrace();
            throw new RuntimeException("Kafka send failed", e);
        }
    }
}
