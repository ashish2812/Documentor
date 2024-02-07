package org.example.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Locale;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class DocumentMetadata {

    private String metadataId;
    private String documentId;
    private String title;
    private String author;
    private Locale createdAt;

}
