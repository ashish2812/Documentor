package org.example.base.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * @author Ashish
 *
 * @date 26-Feb-2024
 *
 */

public enum UserStatusEnum {

    ACTIVE("ACTIVE",(short)0),
    INACTIVE("INACTIVE",(short)1),
    DELETED("DELETED",(short)2);
    private final String name;
    private final Short value;
    private final Map<String, UserStatusEnum> stringMap = new HashMap<String, UserStatusEnum>();

    private UserStatusEnum(String name, Short value){
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

    public UserStatusEnum getUserStatusEnum(String key){
        return stringMap.get(key);
    }

}
