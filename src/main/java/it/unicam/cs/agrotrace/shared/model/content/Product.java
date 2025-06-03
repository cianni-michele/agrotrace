package it.unicam.cs.agrotrace.shared.model.content;

import it.unicam.cs.agrotrace.shared.model.user.author.Author;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Product extends AbstractContent {

    @Builder
    public Product(UUID id, Author author, String title, String description, ValidationStatus validationStatus) {
        super(id, author, title, description, validationStatus);
    }
}
