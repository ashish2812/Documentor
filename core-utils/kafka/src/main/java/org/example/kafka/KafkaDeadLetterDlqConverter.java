package org.example.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.example.base.DocumentoConstants;
import org.example.pojo.dto.KafkaDeadLetterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaDeadLetterDlqConverter {


    @Autowired
    private ObjectMapper objectMapper;
    private static final Charset charset = StandardCharsets.UTF_8;
    Map<String, String> headersValues = new LinkedHashMap<>();

    public ConsumerRecord<?, ?> kafkaDeadLetter(ConsumerRecord<?, ?> record, Exception ex) {

        String topic = record.topic();
        int partition = record.partition();
        String messageId = "";
        for (Header header : record.headers()) {
            if (header.key().equalsIgnoreCase(DocumentoConstants.MESSAGE_ID)) {
                messageId = new String(header.value(), charset); // for UTF-8 encoding
            } else {
                String headerKey = header.key();
                String value = new String(header.value(), charset); // for UTF-8 encoding
                headersValues.put(headerKey, value);
            }
        }
        log.info("\nMessageId: " + messageId + "\n"
                + " Topic: " + record.topic() + "\n"
                + " Class: " + record.key() + "\n"
                + " data: " + record.value() + "\n"
                + " Exception: " + ex.getLocalizedMessage()
        );

        KafkaDeadLetterDto kafkaDeadLetterDto = KafkaDeadLetterDto
                .builder()
                .messageId(messageId)
                .data(record.value().toString())
                .headers(headersValues.isEmpty() ? null : headersValues.toString())
                .isMailSent(Boolean.FALSE)
                .exceptionClass(ex.getClass().toString())
                .exceptionMessage(ex.getLocalizedMessage())
                .isRetryableException(Boolean.TRUE)
                .createdOn(LocalDateTime.now())
                .clazzName(record.key().toString())
                .topicName(record.topic())
                .status((short) 0)
                .build();
        String value;
        long offset = record.offset();
        try {
            value = objectMapper.writeValueAsString(kafkaDeadLetterDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new ConsumerRecord<>(topic, partition, offset, record.key(), value);

    }
}
