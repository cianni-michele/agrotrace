package it.unicam.cs.agrotrace.validator;

import it.unicam.cs.agrotrace.shared.model.content.Content;

/**
 * Interface for validating content.
 *
 * @param <C> the certificationType of content to validate
 */
@FunctionalInterface
public interface ContentValidator<C extends Content> {

    /**
     * Validates the content.
     *
     * @param content the content to validate
     * @return a {@link ValidationResult} containing validation errors, if any
     */
    ValidationResult validate(C content);
}
