package org.example.base.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ashish
 *
 * @date 26-Feb-2024
 *
 */
public enum StatusEnum {

    SUCCESS("SUCCESS",(short)0),
    FAILED("FAILED",(short)1);
    private final String name;
    private final Short value;
    private final Map<String, StatusEnum> stringMap = new HashMap<String, StatusEnum>();

    private StatusEnum(String name, Short value){
        stringMap.put(this.toString(), this);
        this.name = name;
        this.value = value;
    }
    public String getName(){
        return name;
    }

    public Short getValue(){
        return value;
    }

    public StatusEnum getStatusEnum(String key){
        return stringMap.get(key);
    }

}
