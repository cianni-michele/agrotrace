package it.unicam.cs.agrotrace.service;

import it.unicam.cs.agrotrace.exception.ProductValidationException;
import it.unicam.cs.agrotrace.repository.ContentRepository;
import it.unicam.cs.agrotrace.shared.entity.content.ProductEntity;
import it.unicam.cs.agrotrace.shared.model.content.Product;
import it.unicam.cs.agrotrace.util.mapper.content.ProductMapper;
import it.unicam.cs.agrotrace.validator.ContentValidator;
import it.unicam.cs.agrotrace.validator.ValidationResult;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ContentRepository productRepository;

    private final ContentValidator<Product> productValidator;

    private final ProductMapper productMapper;

    public ProductServiceImpl(ContentRepository productRepository,
                              ContentValidator<Product> productValidator,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productValidator = productValidator;
        this.productMapper = productMapper;
    }

    @Override
    public Product saveProduct(Product product) {
        return Optional.ofNullable(product)
                .map(this::validateAndSaveProduct)
                .orElseThrow(() -> new IllegalArgumentException("Product cannot be null"));
    }

    private Product validateAndSaveProduct(Product product) {
        ValidationResult validationResult = productValidator.validate(product);

        if (!validationResult.isValid()) {
            throw new ProductValidationException(validationResult);
        }
        ProductEntity entity = productMapper.entityFromModel(product);

        ProductEntity savedEntity = productRepository.save(entity);

        return productMapper.modelFromEntity(savedEntity);
    }
}
