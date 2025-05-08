package com.apica_2.assignment.service;

import com.apica_2.assignment.entity.Events;
import com.apica_2.assignment.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {

    @Autowired
    private EventRepo eventsRepository;

    @KafkaListener(topics = "user-events", groupId = "journal-group")
    public void consume(String message) {
        System.out.println("Received message from Kafka: " + message);

        if (message.contains(",")) {
            String[] parts = message.split(",");
            String eventName = parts[0];
            String userId = parts[1];

            Events event = new Events();
            event.setEventName(eventName);
            event.setUserId(userId);

            eventsRepository.save(event);
        }
    }
}
