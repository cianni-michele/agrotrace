package it.unicam.cs.agrotrace.service;

import it.unicam.cs.agrotrace.exception.ProductValidationException;
import it.unicam.cs.agrotrace.repository.ContentRepository;
import it.unicam.cs.agrotrace.shared.entity.content.ProductEntity;
import it.unicam.cs.agrotrace.shared.model.content.Product;
import it.unicam.cs.agrotrace.util.mapper.content.ProductMapper;
import it.unicam.cs.agrotrace.util.mapper.user.author.AuthorMapper;
import it.unicam.cs.agrotrace.validator.ContentValidator;
import it.unicam.cs.agrotrace.validator.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static it.unicam.cs.agrotrace.shared.model.content.ValidationStatus.*;
import static it.unicam.cs.agrotrace.util.AuthorTestUtils.*;
import static it.unicam.cs.agrotrace.util.ContentTestUtils.*;
import static it.unicam.cs.agrotrace.util.ContentTestUtils.TEST_CONTENT_ID;
import static it.unicam.cs.agrotrace.util.UploadedFileTestUtils.TEST_FILE_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ContentRepository productRepository;

    @Mock
    private ContentValidator<Product> productValidator;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        AuthorMapper authorMapper = new AuthorMapper();
        ProductMapper productMapper = new ProductMapper(authorMapper);
        productService = new ProductServiceImpl(
                productRepository,
                productValidator,
                productMapper
        );
    }

    @Test
    void saveProduct_shouldThrowException_whenProductIsNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> productService.saveProduct(null),
                "Product cannot be null"
        );
    }

    @Test
    void saveProduct_shouldThrowException_whenProductIsInvalid() {
        Product product = buildTestProductContent(null, TEST_PRODUCER_ID, PENDING);

        when(productValidator.validate(product)).thenReturn(invalidResult(product));

        assertThrows(
                ProductValidationException.class,
                () -> productService.saveProduct(product)
        );
    }

    private ValidationResult invalidResult(Product product) {
        return new ValidationResult(product, Collections.singletonMap("error", "Invalid product"));
    }

    @Test
    void saveProduct_shouldReturnProduct_whenProductIsValid() {
        Product product = buildTestProductContent(null, TEST_PRODUCER_ID, PENDING);

        when(productValidator.validate(product)).thenReturn(validResult(product));

        when(productRepository.save(any(ProductEntity.class)))
                .thenReturn(buildTestProductEntity(TEST_CONTENT_ID, TEST_PRODUCER_ID, TEST_FILE_ID, TEST_FILE_ID));

        Product expected = buildTestProductContent(TEST_CONTENT_ID, TEST_PRODUCER_ID, PENDING);
        Product actual = productService.saveProduct(product);
        assertEquals(expected, actual);
    }


    private ValidationResult validResult(Product product) {
        return new ValidationResult(product, Collections.emptyMap());
    }

}
