package it.unicam.cs.agrotrace.util.mapper.content;

import it.unicam.cs.agrotrace.shared.entity.content.ProductEntity;
import it.unicam.cs.agrotrace.shared.model.content.Product;
import it.unicam.cs.agrotrace.util.mapper.user.author.AuthorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static it.unicam.cs.agrotrace.shared.model.content.ValidationStatus.*;
import static it.unicam.cs.agrotrace.shared.model.content.ValidationStatus.PENDING;
import static it.unicam.cs.agrotrace.util.AuthorTestUtils.TEST_PRODUCER_ID;
import static it.unicam.cs.agrotrace.util.ContentTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        AuthorMapper authorMapper = new AuthorMapper();
        productMapper = new ProductMapper(authorMapper);
    }

    @Test
    void entityFromModel() {
        Product product = buildTestProductContent(TEST_CONTENT_ID, TEST_PRODUCER_ID, PENDING);

        ProductEntity entity = productMapper.entityFromModel(product);

        assertNotNull(entity);
        assertEquals(product.getId(), entity.getId());
        assertEquals(product.getTitle(), entity.getTitle());
        assertEquals(product.getDescription(), entity.getDescription());
        assertEquals(product.getValidationStatus().name(), entity.getValidationStatus());
        assertEquals(product.getAuthor().getId(), entity.getAuthor().getId());
        assertEquals(product.getPrice(), entity.getPrice());
        assertEquals(product.getQuantity(), entity.getQuantity());
        assertEquals(product.getImages().size(), entity.getImages().size());
        assertEquals(product.getCertifications().size(), entity.getCertifications().size());
    }

    @Test
    void modelFromEntity() {
        ProductEntity entity = buildTestProductEntity(TEST_CONTENT_ID, TEST_PRODUCER_ID);

        Product product = productMapper.modelFromEntity(entity);

        assertNotNull(product);
        assertEquals(entity.getId(), product.getId());
        assertEquals(entity.getTitle(), product.getTitle());
        assertEquals(entity.getDescription(), product.getDescription());
        assertEquals(valueOf(entity.getValidationStatus()), product.getValidationStatus());
        assertEquals(entity.getAuthor().getId(), product.getAuthor().getId());
        assertEquals(entity.getPrice(), product.getPrice());
        assertEquals(entity.getQuantity(), product.getQuantity());
        assertEquals(entity.getImages().size(), product.getImages().size());
        assertEquals(entity.getCertifications().size(), product.getCertifications().size());
    }

    @Disabled("TODO: Implement this test")
    @Test
    void viewFromModel() {
        // TODO: Implement this test
    }
}