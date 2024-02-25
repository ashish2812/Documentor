package org.example.datastoreretrieveservice.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.example.datastoreretrieveservice.model.KafkaDeadLetter;
import org.example.datastoreretrieveservice.repository.KafkaRetryRepository;
import org.example.kafka.producer.NotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Async
@Slf4j
public class DataStoreStroreScheduler {

    @Autowired
    KafkaRetryRepository kafkaRetryRepository;

    @Autowired
    NotificationProducer notificationProducer;

    @Value("${spring.scheduler.fixed-delay}")
    Long fixedDelay;


    @Scheduled(fixedDelayString = "${spring.scheduler.fixed-delay}")
    public void cronJobSch() {
        List<KafkaDeadLetter> kafkaDeadLetters = kafkaRetryRepository.getDataBystatusAndIsmailSent();
        kafkaDeadLetters.forEach(kafkaDeadLetter -> notificationProducer.publish(kafkaDeadLetter.getTopicName(),
                kafkaDeadLetter.getClazzName(), kafkaDeadLetter.getData(), kafkaDeadLetter.getMessageId()));
        log.info("kafkaDeadLettersRetry size: " + kafkaDeadLetters.size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        log.info("Java cron job expression:: " + strDate);
    }
}
