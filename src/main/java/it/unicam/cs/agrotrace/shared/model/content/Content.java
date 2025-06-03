package it.unicam.cs.agrotrace.shared.model.content;


import it.unicam.cs.agrotrace.shared.model.user.author.Author;

import java.util.UUID;

public interface Content {

    /**
     * Returns the unique identifier of the content.
     *
     * @return the unique identifier of the content
     */
    UUID getId();

    /**
     * Returns the author of the content.
     *
     * @return the author of the content
     */
    Author getAuthor();

    /**
     * Returns the title of the content.
     *
     * @return the title of the content
     */
    String getTitle();

    /**
     * Returns the description of the content.
     *
     * @return the description of the content
     */
    String getDescription();

    /**
     * Returns the validation status of the content.
     *
     * @return the validation status of the content
     */
    ValidationStatus getValidationStatus();

    /**
     * Approves the content, changing its validation status to APPROVED.
     */
    void approve();

    /**
     * Rejects the content, changing its validation status to REJECTED.
     */
    void reject();

    /**
     * Marks the content as needing correction, changing its validation status to NEEDS_CORRECTION.
     */
    void markAsNeedsCorrection();
}
