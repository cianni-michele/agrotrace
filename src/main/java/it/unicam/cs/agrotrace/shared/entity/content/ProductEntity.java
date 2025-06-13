package it.unicam.cs.agrotrace.shared.entity.content;

import it.unicam.cs.agrotrace.shared.entity.file.CertificationEntity;
import it.unicam.cs.agrotrace.shared.entity.file.ImageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "products")
public class ProductEntity extends ContentEntity {

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;

    @JoinColumn(name = "images", nullable = false)
    private List<ImageEntity> images;

    @JoinColumn(name = "certifications", nullable = false)
    private List<CertificationEntity> certifications;

}
