package org.example.base.utils;


import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class DocumentorUtils {

    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}
