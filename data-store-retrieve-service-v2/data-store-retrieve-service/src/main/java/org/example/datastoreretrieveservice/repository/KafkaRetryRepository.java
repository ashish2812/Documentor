package org.example.datastoreretrieveservice.repository;

import org.example.datastoreretrieveservice.model.KafkaDeadLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ashish
 * @date 04-Feb-2024
 *
 */

@Repository
public interface KafkaRetryRepository extends JpaRepository<KafkaDeadLetter,Integer> {

    KafkaDeadLetter findByMessageId(String messageId);

    @Query(value = "Select kfq from KafkaDeadLetter as kfq " +
            "where kfq.status  = 1  " +
            "and  kfq.isMailSent = false " +
            "and kfq.isRetryableException = true and kfq.totalAttempts<3")
    List<KafkaDeadLetter> getDataBystatusAndIsmailSent();
}
