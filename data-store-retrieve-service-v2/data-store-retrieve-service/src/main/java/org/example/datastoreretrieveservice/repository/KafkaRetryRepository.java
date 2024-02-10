package org.example.datastoreretrieveservice.repository;

import org.example.datastoreretrieveservice.model.KafkaDeadLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface KafkaRetryRepository extends JpaRepository<KafkaDeadLetter,Integer> {

    KafkaDeadLetter findByMessageId(String messageId);
}
