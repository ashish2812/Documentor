package org.example.base.exceptions;

public class KafkaException extends RuntimeException{

    private static final long serialVersionUID = -336865526623902363L;

    public KafkaException(String message) {
        super(message);
    }

    public KafkaException(Throwable cause) {
        super(cause);
    }

    public KafkaException(String message, Throwable cause) {
        super(message, cause);
    }
}
