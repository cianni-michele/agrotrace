package it.unicam.cs.agrotrace.shared.model.user.author;

import it.unicam.cs.agrotrace.shared.model.content.Product;
import it.unicam.cs.agrotrace.shared.model.content.ProductDetails;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Producer extends AbstractAuthor {

    @Builder
    public Producer(Long id, String name, String username, String password) {
        super(id, name, username, password, Role.PRODUCER);
    }

    public Product createProduct(ProductDetails details) {
        if (details == null) {
            throw new NullPointerException("Product details cannot be null");
        }

        return Product.builder()
                .author(this)
                .title(details.title())
                .description(details.description())
                .validationStatus(ValidationStatus.PENDING)
                .price(details.price())
                .quantity(details.quantity())
                .images(new ArrayList<>())
                .certifications(new ArrayList<>())
                .build();
    }
}
