package org.example.documentprocessingservice.services;

public interface NotificationService {

    public void publish(String topic, String className, Object value);
    public void publish(String topic, int partition, String className, Object value);
}
