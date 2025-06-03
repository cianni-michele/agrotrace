package it.unicam.cs.agrotrace.exception;

public class VerificationNotFoundException extends RuntimeException {

    public VerificationNotFoundException(Long id) {
        super("Verification not found with id: " + id);
    }
}
