package it.unicam.cs.agrotrace.exception;

import java.util.UUID;

public class ContentNotFoundException extends RuntimeException {

    public ContentNotFoundException(String message) {
        super(message);
    }

    public ContentNotFoundException(UUID contentId) {
        super("Content with ID " + contentId + " not found.");
    }
}
