package org.example.documentprocessingservice.serviceImp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.documentprocessingservice.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class NotificationServiceImp implements NotificationService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public void publish(String topic, String className, Object value) {

        ProducerRecord<String, String> record = null;

        try {
            record = new ProducerRecord<String, String>(topic, className, objectMapper.writeValueAsString(value));
        } catch (Exception e) {
            log.error("Failed to serialize message to send to topic: " + topic, e);
        }

        publishRecord(record);

    }


    @Override
    public void publish(String topic, int partition, String className, Object value) {

    }

    private void publishRecord(ProducerRecord<String, String> record) {
    }
}
