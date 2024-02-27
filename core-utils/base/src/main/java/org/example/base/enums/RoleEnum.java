package org.example.base.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Ashish
 *
 * @date 26-Feb-2024
 *
 */
public enum RoleEnum {

    ADMIN("ADMIN",(short)0),
    EDITOR("EDITOR",(short)1);

    private final Map<String, RoleEnum> stringMap = new HashMap<String, RoleEnum>();
    private final String name;
    private final Short value;


    RoleEnum(String name, Short value) {
        this.name = name;
        this.value = value;
        stringMap.put(this.toString(), this);
    }

    public String getName() {
        return name;
    }

    public Short getValue() {
        return value;
    }

    public RoleEnum getRoleEnum(String key){
        return stringMap.get(key);
    }
}
