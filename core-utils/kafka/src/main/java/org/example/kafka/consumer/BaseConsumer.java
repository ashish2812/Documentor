package org.example.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.example.base.constants.DocumentoConstants;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author Ashish
 * @date 04-Feb-2024
 *
 */

@Log4j2
public abstract class BaseConsumer<T> {

	@Autowired
	protected ObjectMapper objectMapper;

	protected boolean isRecordNotEmpty(ConsumerRecords<String, String> records) {
		return Objects.nonNull(records) && !records.isEmpty();
	}

	protected T getDto(ConsumerRecord<String, String> record) {

		record.headers().forEach(header -> {
			if (DocumentoConstants.MESSAGE_ID.equalsIgnoreCase(header.key()))
				MDC.put(DocumentoConstants.GUID, new String(header.value()));
		});

		T data = null;

		log.info("BaseConsumer:: Received Payload {}", record);

		try {

			if (StringUtils.isNotBlank(record.key())) {

				@SuppressWarnings("unchecked")
				Class<T> clazz = (Class<T>) Class.forName(record.key());

				log.info("BaseConsumer:: Retrived class: {}", clazz);

				data = objectMapper.readValue(record.value(), clazz);

				log.info("BaseConsumer:: Retrieved object: {}", data);

			} else {
				log.warn("BaseConsumer:: Kafka Record Missing key");
			}

		} catch (Exception e) {
			log.error("BaseConsumer:: Error casting Kafka record to desrired dto: ", e);
		} finally {
			MDC.remove(DocumentoConstants.GUID);
		}

		return data;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected T getDto(ConsumerRecord<String, String> record, Class clazz) {

		record.headers().forEach(header -> {
			if (DocumentoConstants.MESSAGE_ID.equalsIgnoreCase(header.key()))
				MDC.put(DocumentoConstants.GUID, new String(header.value()));
		});

		T data = null;

		log.info("BaseConsumer:: Received Payload {}", record);

		try {

			if (Objects.nonNull(clazz)) {

				log.info("BaseConsumer:: Retrived class: {}", clazz);

				data = (T) objectMapper.readValue(record.value(), clazz);

				log.info("BaseConsumer:: Retrieved object: {}", data);

			} else {
				log.warn("BaseConsumer:: Kafka Record Missing key");
			}

		} catch (Exception e) {
			log.error("BaseConsumer:: Error casting Kafka record to desrired dto: ", e);
		} finally {
			MDC.remove(DocumentoConstants.GUID);
		}

		return data;
	}
}