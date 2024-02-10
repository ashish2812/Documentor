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
        consumerRecord = kafkaDeadLetterDlqConverter.kafkaDeadLetter(consumerRecord,e);
        recoverer.accept(consumerRecord, e);
        log.info("""
                        Sending the exception to dlq for\s
                         topic: {},\s
                         partition: {}\s
                        headers: {}\s
                        """,
                consumerRecord.topic(),
                consumerRecord.partition(),
                consumerRecord.headers().toString()
        );
    }
}
