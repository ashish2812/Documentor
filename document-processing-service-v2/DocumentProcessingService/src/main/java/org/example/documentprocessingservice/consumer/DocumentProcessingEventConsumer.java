package org.example.documentprocessingservice.consumer;

import org.example.kafka.consumer.BaseConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentProcessingEventConsumer<T> extends BaseConsumer<T> {



}
