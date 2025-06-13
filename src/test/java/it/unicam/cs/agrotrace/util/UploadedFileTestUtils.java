package it.unicam.cs.agrotrace.util;

import it.unicam.cs.agrotrace.shared.entity.file.CertificationEntity;
import it.unicam.cs.agrotrace.shared.entity.file.ImageEntity;
import it.unicam.cs.agrotrace.shared.model.file.Certification;
import it.unicam.cs.agrotrace.shared.model.file.Image;

import java.nio.file.Path;
import java.util.UUID;

public final class UploadedFileTestUtils {

    public static final UUID TEST_FILE_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    public static final String TEST_IMAGE_PATH = "images/test-image.jpg";
    public static final String TEST_IMAGE_DESCRIPTION = "test image description";
    public static final String TEST_CERTIFICATION_PATH = "certifications/test-certification.pdf";

    public static Image buildTestImageUploadedFile(UUID imageId) {
        return Image.builder()
                .id(imageId) // ID can be set to null for testing
                .path(Path.of(TEST_IMAGE_PATH))
                .description(TEST_IMAGE_DESCRIPTION)
                .build();
    }

    public static Certification buildTestCertificationUploadedFile(UUID certificationId) {
        return Certification.builder()
                .id(certificationId) // ID can be set to null for testing
                .path(Path.of(TEST_CERTIFICATION_PATH))
                .type(Certification.Type.BIO)
                .build();
    }

    public static ImageEntity buildTestImageEntity() {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(TEST_FILE_ID);
        imageEntity.setPath(TEST_IMAGE_PATH);
        imageEntity.setDescription(TEST_IMAGE_DESCRIPTION);
        return imageEntity;
    }

    public static CertificationEntity buildTestCertificationEntity() {
        CertificationEntity certificationEntity = new CertificationEntity();
        certificationEntity.setId(TEST_FILE_ID);
        certificationEntity.setPath(TEST_CERTIFICATION_PATH);
        certificationEntity.setType("BIO");
        return certificationEntity;
    }

}
