package it.unicam.cs.agrotrace.shared.entity.content;

import it.unicam.cs.agrotrace.shared.entity.file.CertificationEntity;
import it.unicam.cs.agrotrace.shared.entity.file.ImageEntity;
import jakarta.persistence.*;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private List<ImageEntity> images;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private List<CertificationEntity> certifications;

}
