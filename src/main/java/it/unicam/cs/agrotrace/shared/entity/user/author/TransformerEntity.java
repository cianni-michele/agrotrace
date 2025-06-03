package it.unicam.cs.agrotrace.shared.entity.user.author;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("transformer")
public class TransformerEntity extends AuthorEntity {
}
