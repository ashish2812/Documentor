package org.example.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Locale;

/**
 * @author Ashish
 *
 * @date 07-Feb-2024
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class NotificationDetails {

    private String notificationId;
    private String userId;
    private String message;
    private Locale created_at;
    private String read_status; //TODO: make this enum;
}
