package org.example.documentprocessingservice.services;

public interface KafkaServiceProducer {

    void sentMessage(String message);
}
