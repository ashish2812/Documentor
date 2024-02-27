package org.example.base.exceptions;

/**
 * @author Ashish
 *
 * @date 26-Feb-2024
 *
 */
public class DocumentorValidationException extends RuntimeException{

    private static final long serialVersionUID = -3368655266237942363L;

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
