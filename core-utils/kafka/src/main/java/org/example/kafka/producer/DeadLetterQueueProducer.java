package org.example.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.example.base.DocumentoConstants;
import org.example.kafka.KafkaDeadLetterDlqConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.function.BiFunction;

/**
 * @author Ashish
 *
 * @date 04-Feb-2024
 *
 */

@Component
@Slf4j
public class DeadLetterQueueProducer {

    @Autowired
    KafkaDeadLetterDlqConverter kafkaDeadLetterDlqConverter;

    private static final BiFunction<ConsumerRecord<?, ?>, Exception, TopicPartition> DEFAULT_DESTINATION_RESOLVER =
            (consumerRecord, e) -> new TopicPartition(consumerRecord.topic() + "-dlq-topic", consumerRecord.partition());

    public void sendToDlqTopic(KafkaTemplate<?, ?> kafkaTemplate,
                               ConsumerRecord<?, ?> consumerRecord,
                               Exception e) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate, DEFAULT_DESTINATION_RESOLVER);
        consumerRecord = kafkaDeadLetterDlqConverter.kafkaDeadLetter(consumerRecord, e, null);
        recoverer.accept(consumerRecord, e);
        log.info("Sending the exception to dlq for topic: {}, partition: {} headers: {}", consumerRecord.topic(), consumerRecord.partition(), consumerRecord.headers().toString());
    }

    public void sendDataToDlqToUpdateForSuccess(KafkaTemplate<?, ?> kafkaTemplate, ConsumerRecord<?, ?> consumerRecord) {
        consumerRecord = kafkaDeadLetterDlqConverter.kafkaDeadLetter(consumerRecord, null, (short) 0);
        log.info("Preparing request for success: {}", consumerRecord.value());
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate, DEFAULT_DESTINATION_RESOLVER);
        recoverer.accept(consumerRecord, new Exception());

    }
}
