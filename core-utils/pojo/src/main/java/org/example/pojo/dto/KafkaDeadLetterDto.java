package org.example.pojo.dto;

import lombok.*;

import java.time.LocalDateTime;
/**
 * @author Ashish
 *
 * @date 07-Feb-2024
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KafkaDeadLetterDto{

    private String messageId;
    private String topicName;
    private String data;
    private LocalDateTime createdOn;
    private LocalDateTime lastAttemptedOn;
    private LocalDateTime modifiedOn;
    private Integer totalAttempts;
    private String clazzName;
    private String headers;
    private String exceptionClass;
    private String exceptionMessage;
    private Boolean isRetryableException;
    private Boolean isMailSent;
    private Short status; //TODO: Add it in ENUM: FAILED->1, SUCCESS->0

}