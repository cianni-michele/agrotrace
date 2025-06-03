package it.unicam.cs.agrotrace.shared.model.content;

import it.unicam.cs.agrotrace.shared.model.user.author.Author;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public abstract class AbstractContent implements Content {
    protected final UUID id;
    protected final Author author;
    protected final String title;
    protected final String description;
    protected ValidationStatus validationStatus;

    protected AbstractContent(UUID id, Author author, String title, String description, ValidationStatus validationStatus) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.validationStatus = validationStatus;
    }

    @Override
    public void approve() {
        this.validationStatus = ValidationStatus.APPROVED;
    }

    @Override
    public void reject() {
        this.validationStatus = ValidationStatus.REJECTED;
    }

    @Override
    public void markAsNeedsCorrection() {
        this.validationStatus = ValidationStatus.NEEDS_CORRECTION;
    }
}
