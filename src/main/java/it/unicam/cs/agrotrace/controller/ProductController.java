package it.unicam.cs.agrotrace.controller;


import it.unicam.cs.agrotrace.exception.UnauthorizedOperationException;
import it.unicam.cs.agrotrace.rest.request.CertificationMetadataRequest;
import it.unicam.cs.agrotrace.rest.request.ImageMetadataRequest;
import it.unicam.cs.agrotrace.rest.request.ProductDetailsRequest;
import it.unicam.cs.agrotrace.service.ProductService;
import it.unicam.cs.agrotrace.service.notification.NotificationService;
import it.unicam.cs.agrotrace.service.storage.FileStorageService;
import it.unicam.cs.agrotrace.shared.model.file.CertificationDetails;
import it.unicam.cs.agrotrace.shared.model.file.ImageDetails;
import it.unicam.cs.agrotrace.shared.model.content.Product;
import it.unicam.cs.agrotrace.shared.model.content.ProductDetails;
import it.unicam.cs.agrotrace.shared.model.file.Certification;
import it.unicam.cs.agrotrace.shared.model.file.Image;
import it.unicam.cs.agrotrace.shared.model.user.author.Producer;
import it.unicam.cs.agrotrace.util.mapper.content.ProductMapper;
import it.unicam.cs.agrotrace.util.mapper.file.CertificationMapper;
import it.unicam.cs.agrotrace.util.mapper.file.ImageMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    private final ProductMapper productMapper;

    private final FileStorageService<Image, ImageDetails> imageStorageService;

    private final ImageMapper imageMapper;

    private final FileStorageService<Certification, CertificationDetails> certificationStorageService;

    private final CertificationMapper certificationMapper;

    private final NotificationService notificationService;

    public ProductController(ProductService productService,
                             ProductMapper productMapper,
                             FileStorageService<Image, ImageDetails> imageStorageService,
                             ImageMapper imageMapper,
                             FileStorageService<Certification, CertificationDetails> certificationStorageService,
                             CertificationMapper certificationMapper,
                             NotificationService notificationService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.imageStorageService = imageStorageService;
        this.imageMapper = imageMapper;
        this.certificationStorageService = certificationStorageService;
        this.certificationMapper = certificationMapper;
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createProduct(
            Authentication auth,
            @RequestPart ProductDetailsRequest details,
            @RequestPart List<ImageMetadataRequest> imagesMetadata,
            @RequestPart(value = "images") List<MultipartFile> imagesFiles,
            @RequestPart List<CertificationMetadataRequest> certificationsMetadata,
            @RequestPart(value = "certifications") List<MultipartFile> certificationsFiles
    ) {

        if (!(auth.getPrincipal() instanceof Producer producer)) {
            throw new UnauthorizedOperationException();
        }

        if (areFilesAndMetadataMismatched(imagesFiles, imagesMetadata) ||
            areFilesAndMetadataMismatched(certificationsFiles, certificationsMetadata)) {
            throw new IllegalArgumentException("Files and metadata lists must have the same size.");
        }

        ProductDetails productDetails = productMapper.modelFromRequest(details);

        Product product = producer.createProduct(productDetails);

        storeAndAddImagesToProduct(imagesMetadata, imagesFiles, product);

        storeAndAddCertificationsToProduct(certificationsMetadata, certificationsFiles, product);

        productService.saveProduct(product);

        notificationService.notifyCurators(product);

        return ResponseEntity.noContent().build();
    }

    private boolean areFilesAndMetadataMismatched(List<MultipartFile> files, List<?> metadata) {
        if (files == null || metadata == null)
            return false;
        if (files.isEmpty() || metadata.isEmpty())
            return false;
        return files.size() != metadata.size();
    }

    private void storeAndAddImagesToProduct(List<ImageMetadataRequest> imagesMetadata,
                                            List<MultipartFile> imagesFiles,
                                            Product product) {
        for (int i = 0; i < imagesFiles.size(); i++) {
            Image image = imageStorageService.storeFile(
                    imagesFiles.get(i),
                    imageMapper.modelFromRequest(imagesMetadata.get(i))
            );
            product.addImage(image);
        }
    }

    private void storeAndAddCertificationsToProduct(List<CertificationMetadataRequest> certificationsMetadata,
                                                    List<MultipartFile> certificationsFiles,
                                                    Product product) {
        for (int i = 0; i < certificationsFiles.size(); i++) {
            Certification certification = certificationStorageService.storeFile(
                    certificationsFiles.get(i),
                    certificationMapper.modelFromRequest(certificationsMetadata.get(i))
            );
            product.addCertification(certification);
        }
    }
}
