package it.unicam.cs.agrotrace.validator;

import it.unicam.cs.agrotrace.shared.model.file.Certification;
import it.unicam.cs.agrotrace.shared.model.file.Image;
import it.unicam.cs.agrotrace.shared.model.content.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductValidatorTest {

    private ProductValidator productValidator;

    @BeforeEach
    void setUp() {
        productValidator = new ProductValidator();
    }

    @Test
    void shouldThrowExceptionWhenProductIsNull() {
        assertThrows(IllegalArgumentException.class, () -> productValidator.validate(null));
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidProductArguments.class)
    void shouldIsNotValidWhenProductHasInvalidFields(String fieldName, Object fieldValue, String expectedError) {
        Product.ProductBuilder builder = getValidProductBuilder();

        switch (fieldName) {
            case "price" ->             builder.price((Double) fieldValue);
            case "quantity" ->          builder.quantity((Integer) fieldValue);
            case "title" ->             builder.title((String) fieldValue);
            case "imageDescription" ->       builder.description((String) fieldValue);
            case "images" ->            builder.images(List.of((Image[]) fieldValue));
            case "certifications" ->    builder.certifications(List.of((Certification[]) fieldValue));
        }

        Product invalidProduct = builder.build();
        ValidationResult result = productValidator.validate(invalidProduct);

        assertFalse(result.isValid());
        assertTrue(result.getError(fieldName).isPresent());
        assertEquals(expectedError, result.getError(fieldName).get());
    }

    @Test
    void shouldIsValidWhenAllFieldsAreCorrect() {
        Product product = getValidProductBuilder().build();

        ValidationResult result = productValidator.validate(product);

        assertTrue(result.isValid());
    }

    private Product.ProductBuilder getValidProductBuilder() {
        return Product.builder()
                .price(10.0)
                .quantity(5)
                .title("Valid Product")
                .description("This is a valid product imageDescription.")
                .images(Collections.singletonList(Image.builder().build()))
                .certifications(Collections.singletonList(Certification.builder().build()));
    }

    static class InvalidProductArguments implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of("price", 0.0, "Price must be greater than zero"),
                    Arguments.of("quantity", 0, "Quantity must be greater than zero"),
                    Arguments.of("title", "", "Title cannot be empty or null"),
                    Arguments.of("imageDescription", "", "Description cannot be empty or null"),
                    Arguments.of("images", new Image[]{}, "At least one image is required"),
                    Arguments.of("certifications", new Certification[]{}, "At least one certification is required")
            );
        }
    }
}