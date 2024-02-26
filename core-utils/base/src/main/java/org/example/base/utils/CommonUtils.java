package org.example.base.utils;

import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class CommonUtils {

    public static Boolean isValidString(String value){
        return Objects.nonNull(value);
    }

    public static <T> Set<T> enumValuesInList(Class<T> enumCls) {
        T[] arr = enumCls.getEnumConstants();
        return arr == null ? Collections.emptySet() : new HashSet<>(Arrays.asList(arr));
    }
}
