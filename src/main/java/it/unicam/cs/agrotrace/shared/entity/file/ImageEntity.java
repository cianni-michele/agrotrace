package it.unicam.cs.agrotrace.shared.entity.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity(name = "images")
public class ImageEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String description;

}
