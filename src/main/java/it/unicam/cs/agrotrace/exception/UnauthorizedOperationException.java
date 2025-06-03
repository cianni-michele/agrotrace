package it.unicam.cs.agrotrace.exception;

public class UnauthorizedOperationException extends RuntimeException {

    public UnauthorizedOperationException(String message) {
        super(message);
    }

    public UnauthorizedOperationException() {
        super("Unauthorized operation.");
    }
}
