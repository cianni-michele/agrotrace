package it.unicam.cs.agrotrace.exception;

public class NotificationErrorException extends RuntimeException {

    public NotificationErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
