package it.unicam.cs.agrotrace.shared.entity.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "certifications")
public class CertificationEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String path;
}
