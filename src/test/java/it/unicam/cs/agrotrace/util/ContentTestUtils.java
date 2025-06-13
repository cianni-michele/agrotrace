package it.unicam.cs.agrotrace.util;

import it.unicam.cs.agrotrace.shared.entity.content.BundleEntity;
import it.unicam.cs.agrotrace.shared.entity.content.ProductEntity;
import it.unicam.cs.agrotrace.shared.entity.content.ContentEntity;
import it.unicam.cs.agrotrace.shared.entity.content.ProcessEntity;
import it.unicam.cs.agrotrace.shared.model.content.*;
import it.unicam.cs.agrotrace.shared.model.content.Process;

import java.util.List;
import java.util.UUID;

import static it.unicam.cs.agrotrace.util.AuthorTestUtils.*;
import static it.unicam.cs.agrotrace.util.UploadedFileTestUtils.*;

public final class ContentTestUtils {

    public static final UUID TEST_CONTENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final String TEST_CONTENT_TITLE = "Test content title";
    public static final String TEST_CONTENT_DESCRIPTION = "This is a test content description.";
    public static final String TEST_CONTENT_PENDING = "PENDING";
    public static final String TEST_CONTENT_REJECTED = "REJECTED";
    public static final String TEST_CONTENT_APPROVED = "APPROVED";


    public static Content buildTestBundleContent(UUID contentId, Long authorId, ValidationStatus status) {
        return Bundle.builder()
                .id(contentId)
                .author(buildTestDistributorAuthor(authorId))
                .validationStatus(status)
                .title(TEST_CONTENT_TITLE)
                .description(TEST_CONTENT_DESCRIPTION)
                .build();
    }

    public static Content buildTestProcessContent(UUID contentId, Long authorId, ValidationStatus status) {
        return Process.builder()
                .id(contentId)
                .author(buildTestTransformerAuthor(authorId))
                .validationStatus(status)
                .title(TEST_CONTENT_TITLE)
                .description(TEST_CONTENT_DESCRIPTION)
                .build();
    }

    public static Product buildTestProductContent(UUID contentId, Long authorId, ValidationStatus status) {
        return Product.builder()
                .id(contentId)
                .author(buildTestProducerAuthor(authorId))
                .validationStatus(status)
                .title(TEST_CONTENT_TITLE)
                .description(TEST_CONTENT_DESCRIPTION)
                .price(10.0)
                .quantity(2)
                .images(List.of(buildTestImageUploadedFile()))
                .certifications(List.of(buildTestCertificationUploadedFile()))
                .build();
    }

    public static ContentEntity buildTestProcessEntity(UUID contentId, Long authorId) {
        ProcessEntity processEntity = new ProcessEntity();
        processEntity.setId(contentId);
        processEntity.setAuthor(buildTestTransformerEntity(authorId));
        processEntity.setTitle(TEST_CONTENT_TITLE);
        processEntity.setDescription(TEST_CONTENT_DESCRIPTION);
        processEntity.setValidationStatus(TEST_CONTENT_PENDING);
        return processEntity;
    }

    public static ProductEntity buildTestProductEntity(UUID contentId, Long authorId) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(contentId);
        productEntity.setAuthor(buildTestProducerEntity(authorId));
        productEntity.setTitle(TEST_CONTENT_TITLE);
        productEntity.setDescription(TEST_CONTENT_DESCRIPTION);
        productEntity.setValidationStatus(TEST_CONTENT_PENDING);
        productEntity.setPrice(10.0);
        productEntity.setQuantity(2);
        productEntity.setImages(List.of(buildTestImageEntity()));
        productEntity.setCertifications(List.of(buildTestCertificationEntity()));
        return productEntity;
    }

    public static ContentEntity buildTestBundleEntity(UUID contentId, Long authorId) {
        BundleEntity bundleEntity = new BundleEntity();
        bundleEntity.setId(contentId);
        bundleEntity.setAuthor(buildTestDistributorEntity(authorId));
        bundleEntity.setTitle(TEST_CONTENT_TITLE);
        bundleEntity.setDescription(TEST_CONTENT_DESCRIPTION);
        bundleEntity.setValidationStatus(TEST_CONTENT_PENDING);
        return bundleEntity;
    }

}
