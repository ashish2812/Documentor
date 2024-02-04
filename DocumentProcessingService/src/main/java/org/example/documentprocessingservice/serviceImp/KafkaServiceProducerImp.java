//package org.example.documentprocessingservice.serviceImp;
//
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.example.documentprocessingservice.services.KafkaServiceProducer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaServiceProducerImp implements KafkaServiceProducer {
//
//    @Autowired
//    Notifica<String,String> kafkaTemplate;
//
//    @Override
//    public void sentMessage(String message) {
//        ProducerRecord<String, String> record = new ProducerRecord<>("new_topic", "simple_key", message);
//        record.headers().add("headerKey", "headerValue.getBytes()".getBytes());
//        kafkaTemplate.send(record);
//    }
//}
