package org.example.datastoreretrieveservice.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.base.enums.StatusEnum;
import org.example.datastoreretrieveservice.UserServiceAndRetrive;
import org.example.datastoreretrieveservice.model.KafkaDeadLetter;
import org.example.datastoreretrieveservice.model.UserDetails;
import org.example.datastoreretrieveservice.repository.KafkaRetryRepository;
import org.example.datastoreretrieveservice.repository.UserDetailsRepository;
import org.example.kafka.KafkaDeadLetterDlqConverter;
import org.example.kafka.consumer.BaseConsumer;
import org.example.kafka.logger.ConsumerLogger;
import org.example.kafka.producer.DeadLetterQueueProducer;
import org.example.pojo.dto.KafkaDeadLetterDto;
import org.example.pojo.dto.UserRequestDTO;
import org.example.pojo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Ashish
 * @date 04-Feb-2024
 */


@Log4j2
@Service
@Lazy(false)
public class DataStoreEventConsumer<T> extends BaseConsumer<T> {

    @Autowired
    DeadLetterQueueProducer deadLetterQueueProducer;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    KafkaRetryRepository retryRepository;

    @Autowired
    KafkaDeadLetterDlqConverter kafkaDeadLetterDlqConverter;

    @Autowired
    UserServiceAndRetrive userServiceAndRetrive;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @KafkaListener(
            topics = {"${dsrms.kafka.topic.dsrms-create-user}"},
            autoStartup = "${spring.kafka.consumer.autostart}", idIsGroup = false,
            id = "${dsrms.kafka.consumer-grp-id.user-consumer-id}")
    public void receive(ConsumerRecord<String, String> records, Acknowledgment acknowledgment) {

        if (ObjectUtils.isEmpty(records)) {
            return;
        }
        try {
            UserRequestDTO user = objectMapper.readValue(records.value(), UserRequestDTO.class);
            ConsumerLogger.consumerInfoLogging(records);
            UserDetails userDetails = userServiceAndRetrive.createUser(user);
            userDetailsRepository.save(userDetails);
            deadLetterQueueProducer.sendDataToDlqToUpdateForSuccess(kafkaTemplate, records);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            ConsumerLogger.consumerInfoLogging(records);
            deadLetterQueueProducer.sendToDlqTopic(kafkaTemplate, records, e);
            acknowledgment.acknowledge();
        }
    }

    @KafkaListener(
            topicPattern = "${dsrms.kafka.topic.dlt-topic-pattern}",
            autoStartup = "${spring.kafka.consumer.autostart}", idIsGroup = false,
            id = "${dsrms.kafka.consumer-grp-id.dlq-consumer-id}"
    )
    public void receiveDlq(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        if (ObjectUtils.isEmpty(record)) {
            return;
        }
        try {
            ConsumerLogger.consumerInfoLogging(record);
            KafkaDeadLetterDto kafkaDeadLetterDto = objectMapper.readValue(record.value(), KafkaDeadLetterDto.class);
            Short status = kafkaDeadLetterDto.getStatus();
            String messageId = kafkaDeadLetterDto.getMessageId();
            KafkaDeadLetter kafkaDeadLetter = retryRepository.findByMessageId(messageId);
            KafkaDeadLetter savingDataIntoDataBase;
            Boolean isUpdateData = Boolean.FALSE;
            if (Objects.isNull(kafkaDeadLetter)) {
                KafkaDeadLetter deadLetter = KafkaDeadLetter.builder()
                        .data(kafkaDeadLetterDto.getData())
                        .messageId(messageId)
                        .status(status)
                        .exceptionClass(kafkaDeadLetterDto.getExceptionClass())
                        .exceptionMessage(kafkaDeadLetterDto.getExceptionMessage())
                        .isRetryableException(kafkaDeadLetterDto.getIsRetryableException())
                        .headers(kafkaDeadLetterDto.getHeaders())
                        .clazzName(kafkaDeadLetterDto.getClazzName())
                        .topicName(kafkaDeadLetterDto.getTopicName())
                        .totalAttempts(0)
                        .isMailSent(Boolean.FALSE)
                        .createdOn(LocalDateTime.now())
                        .build();
                savingDataIntoDataBase = retryRepository.save(deadLetter);
            } else {
                isUpdateData = Boolean.TRUE;
                KafkaDeadLetter deadLetter = KafkaDeadLetter.builder()
                        .id(kafkaDeadLetter.getId())
                        .data(kafkaDeadLetterDto.getData())
                        .headers(kafkaDeadLetterDto.getHeaders())
                        .messageId(kafkaDeadLetter.getMessageId())
                        .clazzName(kafkaDeadLetterDto.getClazzName())
                        .status(status)
                        .lastAttemptedOn(kafkaDeadLetterDto.getLastAttemptedOn())
                        .topicName(kafkaDeadLetterDto.getTopicName())

                        .totalAttempts(status.equals(StatusEnum.FAILED.getValue())
                                ? kafkaDeadLetter.getTotalAttempts() + 1 :
                                kafkaDeadLetter.getTotalAttempts())

                        .isMailSent(Boolean.FALSE)
                        .exceptionClass(kafkaDeadLetterDto.getExceptionClass())
                        .exceptionMessage(kafkaDeadLetterDto.getExceptionMessage())
                        .isRetryableException(kafkaDeadLetterDto.getIsRetryableException())
                        .createdOn(kafkaDeadLetter.getCreatedOn())
                        .modifiedOn(LocalDateTime.now())
                        .build();
                savingDataIntoDataBase = retryRepository.save(deadLetter);
            }
            log.info("\nSaving to database : {}," +
                            " \nisUpdatedData: {}, " +
                            "\n MessageId: {}",
                    savingDataIntoDataBase,
                    isUpdateData,
                    savingDataIntoDataBase.getMessageId());
            acknowledgment.acknowledge();
        } catch (Exception e) {
            ConsumerLogger.consumerInfoLogging(record);
            log.error("MethodName: receiveDlq ,Error during parsing the json: {}", e.getMessage());
        }
    }
}
