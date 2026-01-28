package com.example.bazarxplatform.Exception;

public class InvalidCardException extends RuntimeException {

    public InvalidCardException(String message) {
        super(message);
    }

    public InvalidCardException(String message, Throwable cause) {
        super(message, cause);
    }
}
