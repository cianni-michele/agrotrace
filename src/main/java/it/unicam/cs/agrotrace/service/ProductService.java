package it.unicam.cs.agrotrace.service;

import it.unicam.cs.agrotrace.exception.ProductValidationException;
import it.unicam.cs.agrotrace.shared.model.content.Product;

public interface ProductService {

    /**
     * Saves a product to the repository after validating it.
     *
     * @param product the product to be saved
     * @return the saved product
     * @throws IllegalArgumentException if the product is null
     * @throws ProductValidationException if the product fails validation
     */
    Product saveProduct(Product product);
}
