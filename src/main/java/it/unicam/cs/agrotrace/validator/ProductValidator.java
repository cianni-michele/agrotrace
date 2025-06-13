package it.unicam.cs.agrotrace.validator;

import it.unicam.cs.agrotrace.shared.model.content.Product;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductValidator implements ContentValidator<Product> {

    private static final String PRICE_ERROR = "Price must be greater than zero";
    private static final String QUANTITY_ERROR = "Quantity must be greater than zero";
    private static final String TITLE_ERROR = "Title cannot be empty or null";
    private static final String DESCRIPTION_ERROR = "Description cannot be empty or null";
    private static final String IMAGES_ERROR = "At least one image is required";
    private static final String CERTIFICATIONS_ERROR = "At least one certification is required";

    @Override
    public ValidationResult validate(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        Map<String, String> errors = new HashMap<>();

        addErrorIfCondition(product.getPrice() <= 0,
                errors, "price", PRICE_ERROR);

        addErrorIfCondition(product.getQuantity() <= 0,
                errors, "quantity", QUANTITY_ERROR);

        addErrorIfCondition(product.getTitle() == null || product.getTitle().isBlank(),
                errors, "title", TITLE_ERROR);

        addErrorIfCondition(product.getDescription() == null || product.getDescription().isBlank(),
                errors, "imageDescription", DESCRIPTION_ERROR);

        addErrorIfCondition(product.getImages() == null || product.getImages().isEmpty(),
                errors, "images", IMAGES_ERROR);

        addErrorIfCondition(product.getCertifications() == null || product.getCertifications().isEmpty(),
                errors, "certifications", CERTIFICATIONS_ERROR);

        return new ValidationResult(product, errors);
    }

    private void addErrorIfCondition(boolean condition, Map<String, String> errors,
                                     String field, String message) {
        if (condition) {
            errors.put(field, message);
        }
    }
}
