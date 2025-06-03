package it.unicam.cs.agrotrace.shared.model.user.admin;

import it.unicam.cs.agrotrace.exception.ContentValidationException;
import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import it.unicam.cs.agrotrace.shared.model.user.AbstractUser;
import lombok.Builder;

import java.util.Objects;

public class Curator extends AbstractUser {

    @Builder
    public Curator(Long id, String name, String username, String password) {
        super(id, name, username, password, Role.CURATOR);
    }

    /**
     * Validates the given content and updates its validation status.
     *
     * @param content the content to validate
     * @param status  the new validation status
     * @throws IllegalArgumentException if content or status is null
     * @throws ContentValidationException if the content's current validation status is not PENDING
     */
    public void validate(Content content, ValidationStatus status) {
        if (Objects.isNull(content)) {
            throw new IllegalArgumentException("Content cannot be null");
        }

        if (Objects.isNull(status)) {
            throw new IllegalArgumentException("Validation status cannot be null");
        }

        if (content.getValidationStatus() != ValidationStatus.PENDING) {
            throw new ContentValidationException(
                    content.getId(),
                    "Content validation status must be PENDING to be updated"
            );
        }

        switch (status) {
            case APPROVED -> content.approve();
            case REJECTED -> content.reject();
            case NEEDS_CORRECTION -> content.markAsNeedsCorrection();
            case PENDING -> throw new ContentValidationException(
                    content.getId(),
                    "Cannot set content validation status to PENDING"
            );
        }
    }

    @Override
    public boolean hasAccessTo(Content content) {
        return true; // Curators have access to all contents
    }
}
