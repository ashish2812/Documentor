package org.example.base.exceptions;

class WrapperException extends RuntimeException {

    private WrapperException(Throwable throwable) {
        super(throwable);
    }

    public static WrapperException wrap(Throwable throwable) {
        return new WrapperException(throwable);
    }

    public Throwable unwrap() {
        return getCause();
    }
}