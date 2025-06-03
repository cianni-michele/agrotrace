package it.unicam.cs.agrotrace.exception;

public class CuratorNotFoundException extends RuntimeException {
    public CuratorNotFoundException(String message) {
        super(message);
    }

    public CuratorNotFoundException(Long curatorId) {
        super("Curator with ID " + curatorId + " not found.");
    }
}
