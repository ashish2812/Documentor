package org.example.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;


@Component
@Slf4j
public class DeadLetterQueueProducer {
    private static final BiFunction<ConsumerRecord<?, ?>, Exception, TopicPartition> DEFAULT_DESTINATION_RESOLVER =
            (consumerRecord, e) -> new TopicPartition(consumerRecord.topic() + "-dlq-topic", consumerRecord.partition());
    public void sendToDlqTopic(KafkaTemplate<?,?> kafkaTemplate,
                                ConsumerRecord<?,?> consumerRecord,
                               Exception e){;

        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate, DEFAULT_DESTINATION_RESOLVER);
        recoverer.accept(consumerRecord,e);

        log.info("Sending the exception to dlq for topic: {}, partition: {}", consumerRecord.topic(),consumerRecord.partition());
    }
}
