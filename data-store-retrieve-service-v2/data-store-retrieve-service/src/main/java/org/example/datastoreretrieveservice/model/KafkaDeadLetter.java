package org.example.datastoreretrieveservice.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "DlqKafkaTable")
public class KafkaDeadLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "messageId", unique = true)
    private String messageId;

    @Column(name = "topicName")
    private String topicName;

    @Column(name = "data")
    private String data;

    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @Column(name = "lastAttemptedOn")
    private LocalDateTime lastAttemptedOn;

    @Column(name = "modifiedOn")
    private LocalDateTime modifiedOn;

    @Column(name = "totalAttempts")
    private Integer totalAttempts;

    @Column(name = "clazzName")
    private String clazzName;

    @Column(name = "headers")
    private String headers;

    @Column(name = "isMailSent")
    private Boolean isMailSent;

    @Column(name= "exceptionClass")
    private String exceptionClass;

    @Column(name= "exceptionMessage")
    private String exceptionMessage;

    @Column(name= "isRetryableException")
    private Boolean isRetryableException;

    @Column(name = "status")
    private Short status; //TODO: Add it in ENUM: FAILED->1, SUCCESS->0

}
