package org.example.base.utils;

import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

import java.util.*;

@UtilityClass
public class CommonUtils {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Boolean isValidString(String value){
        return Objects.nonNull(value) && !value.isEmpty();
    }

    public static <T> Set<T> enumValuesInList(Class<T> enumCls) {
        T[] arr = enumCls.getEnumConstants();
        return arr == null ? Collections.emptySet() : new HashSet<>(Arrays.asList(arr));
    }

    //Converts One class to another class having same fields
    public static <T, U> U convert(T source, Class<U> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }
}
