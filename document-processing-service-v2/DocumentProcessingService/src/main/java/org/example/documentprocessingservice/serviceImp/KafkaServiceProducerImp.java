package org.example.documentprocessingservice.serviceImp;

import org.example.documentprocessingservice.services.KafkaServiceProducer;
import org.example.kafka.producer.NotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceProducerImp implements KafkaServiceProducer {

    @Autowired
    private NotificationProducer notificationProducer;


    @Override
    public void sentMessage(String message) {
        for (int i = 0;i<10;i++) {
            notificationProducer.publish("new_topic", String.class.getName(), "This is the value" + i);
        }
    }
}
