package it.unicam.cs.agrotrace.exception;

public class UnauthorizedOperationException extends RuntimeException {

    public UnauthorizedOperationException() {
        super("Unauthorized operation.");
    }
}
