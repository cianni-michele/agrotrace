package it.unicam.cs.agrotrace.exception;

import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;

import java.util.UUID;

public class ContentValidationException extends RuntimeException {
    public ContentValidationException(String message) {
        super(message);
    }

    public ContentValidationException(UUID contentId, String message) {
        super("Content with ID " + contentId + " validation failed: " + message);
    }
}
