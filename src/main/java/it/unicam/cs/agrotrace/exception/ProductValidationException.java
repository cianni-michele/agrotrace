package it.unicam.cs.agrotrace.exception;

import it.unicam.cs.agrotrace.validator.ValidationResult;

public class ProductValidationException extends RuntimeException {

    public ProductValidationException(ValidationResult result) {
        super(createMessage(result));
    }

    private static String createMessage(ValidationResult result) {
        return result.getValidatedObject() + " validation failed - (errors: " + result.getErrorCount() + "): " +
               result.stream()
                       .map(entry -> "\n" + entry.getKey() + ": " + entry.getValue())
                       .reduce("", String::concat);
    }
}
