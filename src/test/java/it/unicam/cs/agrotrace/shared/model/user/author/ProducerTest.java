package it.unicam.cs.agrotrace.shared.model.user.author;

import it.unicam.cs.agrotrace.shared.model.content.Product;
import it.unicam.cs.agrotrace.shared.model.content.ProductDetails;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ProducerTest {

    private Producer producer;

    @BeforeEach
    void setUp() {
        producer = Producer.builder()
                .id(1L)
                .name("Test Producer")
                .username("test_producer")
                .password("password")
                .build();
    }

    @Test
    void shouldNotCreateProductWithNullDetails() {
        assertThrows(NullPointerException.class, () -> producer.createProduct(null));
    }

    @Test
    void shouldCreateProduct() {
        ProductDetails details = getTestProductDetails();

        Product actual = producer.createProduct(details);

        assertNotNull(actual);
    }

    @Test
    void shouldCreateProductWithAuthorAndPendingValidationStatus() {
        ProductDetails details = getTestProductDetails();

        Product actual = producer.createProduct(details);

        assertEquals(producer, actual.getAuthor());
        assertEquals(ValidationStatus.PENDING, actual.getValidationStatus());
    }

    @Test
    void shouldCreateProductWithCorrectDetails() {
        ProductDetails details = getTestProductDetails();

        Product actual = producer.createProduct(details);

        assertEquals(details.title(), actual.getTitle());
        assertEquals(details.description(), actual.getDescription());
        assertEquals(details.price(), actual.getPrice());
        assertEquals(details.quantity(), actual.getQuantity());
    }

    @Test
    void shouldCreateProductWithoutId() {
        ProductDetails details = getTestProductDetails();

        Product actual = producer.createProduct(details);

        assertNull(actual.getId());
    }

    @Test
    void shouldCreateProductWithoutImagesAndCertifications() {
        ProductDetails details = getTestProductDetails();

        Product actual = producer.createProduct(details);

        assertTrue(actual.getImages().isEmpty());
        assertTrue(actual.getCertifications().isEmpty());
    }

    private ProductDetails getTestProductDetails() {
        return new ProductDetails("Test Product",
                "This is a test product description.",
                10.0,
                5
        );
    }
}