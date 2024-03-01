package org.example.base.exceptions;

import org.example.base.constants.ExceptionCodeConstants;

/**
 * @author Ashish
 *
 * @date 26-Feb-2024
 *
 */
public class DocumentorValidationException extends RuntimeException{

    private static final long serialVersionUID = -3368655266237942363L;

    public final static String DOCUMENTOR_EXCEPTION_VALIDATION_CODE = ExceptionCodeConstants.DOCUMENTOR_EXCEPTION_VALIDATION_CODE;

    public DocumentorValidationException(String message) {
        super(message);
    }

    public DocumentorValidationException(Throwable cause) {
        super(cause);
    }

    public DocumentorValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
