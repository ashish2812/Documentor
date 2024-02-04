/**
 * 
 */
package org.example.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.base.exceptions.KafkaException;
import org.example.base.utils.DocumentorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author naveen.kumar
 *
 * @date 28-Oct-2019
 *
 **/

@Service
@Log4j2
public class NotificationProducer {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void publish(String topic, String className, Object value) {

			ProducerRecord<String, String> record = null;

			try {
				record = new ProducerRecord<String, String>(topic, className, objectMapper.writeValueAsString(value));
			} catch (Exception e) {
				log.error("Failed to serialize message to send to topic: " + topic, e);
			}

			publishRecord(record);
	}

	public void publish(String topic, int partition, String className, Object value) {

		ProducerRecord<String, String> record = null;
		try {
			record = new ProducerRecord<String, String>(topic, partition, className, objectMapper.writeValueAsString(value));
		} catch (Exception e) {
			log.error("Failed to serialize message to send to topic: " + topic, e);
		}

		publishRecord(record);
	}

	private void publishRecord(ProducerRecord<String, String> record) {

		if (Objects.nonNull(record)) {

			String messageId = DocumentorUtils.generateUniqueId();
			record.headers().add("messageId", messageId.getBytes());

			log.info("Publishing records: {}", record);

			CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);

			future.thenAccept(sendResult -> {
				log.info("Message sent successfully! Offset: " + sendResult.getRecordMetadata().offset());
			}).exceptionally(ex -> {
				log.error("Error sending message: " + ex.getMessage());
				throw new KafkaException(ex.getMessage(),ex.getCause());
			});
		}
	}

}