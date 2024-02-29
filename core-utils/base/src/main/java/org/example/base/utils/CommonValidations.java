package org.example.base.utils;

import jakarta.annotation.PostConstruct;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.example.base.enums.RoleEnum;
import org.example.base.enums.UserStatusEnum;
import org.example.base.exceptions.DocumentorValidationException;
import org.example.base.utils.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class CommonValidations {
    public static Set<RoleEnum> roleEnums;
    public static Set<UserStatusEnum> userStatusEnums;

    @PostConstruct
    private void fillAllEnumsIntoList() {
        log.info("Running post Construct");

        roleEnums = CommonUtils.enumValuesInList(RoleEnum.class);
        log.info("Added roles, size: {}", roleEnums.size());

        userStatusEnums = CommonUtils.enumValuesInList(UserStatusEnum.class);
        log.info("Added UserStatusEnum, size: {}", userStatusEnums.size());

    }

    public static <T extends Enum<T>> Boolean validateEnum(String enumValue, Class<T> enumClass) {
        log.info("Validating the Enums");
        if (CommonUtils.isValidString(enumValue)) {
            try {
                //TODO: Fix this retuning: Validating for enumValue: ADMIN, with class: Class
                log.info("Validating for enumValue: {}, with class: {}",enumValue,enumClass.getClass().getSimpleName());
                Enum.valueOf(enumClass, enumValue);
                return true;
            } catch (IllegalArgumentException e) {
                log.info("Validation failed for enum");
                throw new DocumentorValidationException("Not a valid Enum present");
            }
        }

        log.info("Validation failed for enum");
        throw new DocumentorValidationException("Enum value is null or empty");
    }

}
