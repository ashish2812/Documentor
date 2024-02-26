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
public class DocumentDetails {
    private String userId;
    private String documentId;
    private String documentType; //TODO: make this enum;
    private String documentName;
    private Locale documentLastUsed;
    private Locale documentLastViewed;
    private Locale processedAt;
    private String status; //TODO: make this enum;

}
