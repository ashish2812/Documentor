package org.example.kafka.logger;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.example.base.DocumentoConstants;

import java.nio.charset.StandardCharsets;

/**
 * @author Ashish
 * @date 04-Feb-2024
 *
 */

@UtilityClass
@Slf4j
public class ConsumerLogger {

    public static void consumerInfoLogging(ConsumerRecord<String, String> consumerRecord) {

        String messageId = "";
        String topic = consumerRecord.topic();

        for (Header header : consumerRecord.headers()) {
            if (header.key().equals(DocumentoConstants.MESSAGE_ID)) {
                messageId = new String(header.value(), StandardCharsets.UTF_8); // for UTF-8 encoding
                log.info("ConsumerLogger: Key:{}, value: {}", header.key(), messageId);
                break;
            }
        }
        log.info(
                "messageId: {}, topic: {}, key: {}, value: {}, headers: {}", messageId, topic, consumerRecord.key(), consumerRecord.value(), consumerRecord.headers()
        );

    }


}
