package it.unicam.cs.agrotrace.shared.entity.content;

import it.unicam.cs.agrotrace.shared.entity.user.author.AuthorEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class ContentEntity {

    @Id()
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(name = "validation_status", nullable = false)
    private String validationStatus;

}
