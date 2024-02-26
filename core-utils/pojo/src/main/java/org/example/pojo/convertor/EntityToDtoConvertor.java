package org.example.pojo.convertor;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.example.base.exceptions.DocumentorException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
/**
 * @author Ashish
 *
 * @date 12-Feb-2024
 *
 */
@Service
@Log4j2
public class EntityToDtoConvertor {

    private <T extends Object, Y extends Object> void copyFields(T from, Y too) {

        Class<? extends Object> fromClass = from.getClass();
        Field[] fromFields = fromClass.getDeclaredFields();

        Class<? extends Object> tooClass = too.getClass();
        Field[] tooFields = tooClass.getDeclaredFields();

        if (fromFields != null && tooFields != null) {
            for (Field tooF : tooFields) {
                log.debug("toofield name #0 and type #1", tooF.getName(), tooF.getType().toString());
                try {
                    // Check if that fields exists in the other method
                    Field fromF = fromClass.getDeclaredField(tooF.getName());
                    if (fromF.getType().equals(tooF.getType())) {
                        tooF.set(tooF, fromF);
                    }
                } catch (IllegalArgumentException | NoSuchFieldException | IllegalAccessException e) {
                    throw new DocumentorException(e.getMessage());
                }

            }
        }
    }
    }
