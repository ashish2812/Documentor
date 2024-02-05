package org.example.base.exceptions;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.example.base.commonDTO.ResponseDto;
import org.example.base.constants.DocumentorConstants;
import org.example.base.utils.DocumentorUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author Ashish
 *
 * @date 04-Feb-2024
 *
 **/

@RestControllerAdvice
@Log4j2
public class ExceptionCommonAdvice {

    @ExceptionHandler(NoRecordException.class)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public <T> ResponseDto<T> handleNoRecordException(NoRecordException e) {

        String exceptionId = getExceptionId();
        log.error("Got NoRecordException for exceptionId: {} with Message: {}", exceptionId, e);

        return ResponseDto.failure(e.getMessage(), exceptionId);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public <T> ResponseDto<T> handleException(Exception ex) {

        String exceptionId = getExceptionId();
        log.error("Got un-handled exception for exceptionId: {}", exceptionId, ex);

        return ResponseDto.failure(ex.getMessage(), exceptionId);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public <T> ResponseDto<T> handleIllegalArgumentException(IllegalArgumentException e) {

        String exceptionId = getExceptionId();
        log.error("Got IllegalArgumentException for exceptionId: {} with Message {}", exceptionId, e.getMessage());

        return ResponseDto.failure(e.getMessage(), exceptionId);
    }

    @ExceptionHandler(DocumentorException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public <T> ResponseDto<T> handleDocumentorException(DocumentorException e) {

        String exceptionId = getExceptionId();
        log.error("Got DocumentorException for exceptionId: {}", exceptionId, e);

        return ResponseDto.failure(e.getMessage(), exceptionId);
    }


    @ExceptionHandler(KafkaException.class)
    @ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
    public <T> ResponseDto<T> kafkaException(KafkaException e) {

        String exceptionId = getExceptionId();
        log.error("Got DocumentorException for exceptionId: {}", exceptionId, e);

        return ResponseDto.failure(e.getMessage(), exceptionId);
    }



    private String getExceptionId() {
        String exceptionId = MDC.get(DocumentorConstants.GUID);

        if (StringUtils.isBlank(exceptionId)) {
            exceptionId = DocumentorUtils.generateUniqueId();
        }

        return exceptionId;
    }
}
