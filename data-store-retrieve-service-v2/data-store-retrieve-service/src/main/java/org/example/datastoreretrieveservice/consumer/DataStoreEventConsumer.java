package org.example.datastoreretrieveservice.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.kafka.consumer.BaseConsumer;
import org.example.kafka.producer.DeadLetterQueueProducer;
import org.example.pojo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Log4j2
@Service
@Lazy(false)
public class DataStoreEventConsumer<T> extends BaseConsumer<T> {

    @Autowired
    DeadLetterQueueProducer deadLetterQueueProducer;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @KafkaListener(
            topics = {"${dsrms.kafka.topic.dsrms-store-user}"},
            autoStartup = "${spring.kafka.consumer.autostart}", idIsGroup = false,
            id = "user-consumer")
    public void receive(ConsumerRecord<String, String> records, Acknowledgment acknowledgment) {

        if (ObjectUtils.isEmpty(records)) {
            return;
        }
        try {
            User user = objectMapper.readValue(records.value(), User.class);
            log.info("Records for use-->{}", user.getUserName());
            acknowledgment.acknowledge();
        } catch (JsonProcessingException e) {
            deadLetterQueueProducer.sendToDlqTopic(kafkaTemplate,records,e);
            acknowledgment.acknowledge();
            throw new RuntimeException(e);
        }
    }
}
