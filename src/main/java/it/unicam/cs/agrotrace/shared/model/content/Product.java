package it.unicam.cs.agrotrace.shared.model.content;

import it.unicam.cs.agrotrace.shared.model.file.Certification;
import it.unicam.cs.agrotrace.shared.model.file.Image;
import it.unicam.cs.agrotrace.shared.model.user.author.Author;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Product extends AbstractContent {

    private final double price;

    private final int quantity;

    private final List<Image> images;

    private final List<Certification> certifications;

    @Builder
    public Product(UUID id,
                   Author author,
                   String title,
                   String description,
                   ValidationStatus validationStatus,
                   double price,
                   int quantity,
                   List<Image> images,
                   List<Certification> certifications) {
        super(id, author, title, description, validationStatus);
        this.price = price;
        this.quantity = quantity;
        this.images = images;
        this.certifications = certifications;
    }

    public void addImage(Image image) {
        this.images.add(image);
    }

    public void addCertification(Certification certification) {
        this.certifications.add(certification);
    }
}
