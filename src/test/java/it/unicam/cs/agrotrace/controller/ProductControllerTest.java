package it.unicam.cs.agrotrace.controller;

import it.unicam.cs.agrotrace.config.TestSecurityConfig;
import it.unicam.cs.agrotrace.security.context.support.WithProducerUser;
import it.unicam.cs.agrotrace.service.ProductService;
import it.unicam.cs.agrotrace.service.notification.NotificationService;
import it.unicam.cs.agrotrace.service.storage.FileStorageService;
import it.unicam.cs.agrotrace.shared.model.file.CertificationDetails;
import it.unicam.cs.agrotrace.shared.model.file.ImageDetails;
import it.unicam.cs.agrotrace.shared.model.content.Product;
import it.unicam.cs.agrotrace.shared.model.file.Certification;
import it.unicam.cs.agrotrace.shared.model.file.Image;
import it.unicam.cs.agrotrace.util.mapper.content.ProductMapper;
import it.unicam.cs.agrotrace.util.mapper.file.CertificationMapper;
import it.unicam.cs.agrotrace.util.mapper.file.ImageMapper;
import it.unicam.cs.agrotrace.util.mapper.user.author.AuthorMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ExtendWith(SpringExtension.class)
@Import({
        TestSecurityConfig.class,
        AuthorMapper.class,
        ProductMapper.class,
        ImageMapper.class,
        CertificationMapper.class
})
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private NotificationService notificationService;

    @MockitoBean
    private FileStorageService<Image, ImageDetails> imageStorageService;

    @MockitoBean
    private FileStorageService<Certification, CertificationDetails> certificationStorageService;

    @MockitoBean
    private ProductService productService;

    @Nested
    class CreateProductTests {

        @Test
        @WithMockUser(roles = "CUSTOMER")
        void shouldReturnForbidden_whenUserIsNotProducer() throws Exception {
            MockMultipartFile details = buildProductDetails();

            MockMultipartFile imageMetadata = buildImageMetadata();

            MockMultipartFile imageFile = buildImageFile();

            MockMultipartFile certMetadata = buildCertificationMetadata();

            MockMultipartFile certFile = buildCertificationFile();

            mvc.perform(multipart("/api/product")
                            .file(details)
                            .file(imageMetadata)
                            .file(imageFile)
                            .file(certMetadata)
                            .file(certFile))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithProducerUser
        void shouldReturnStatusNoContent_whenCreateProductRequestIsValid() throws Exception {
            MockMultipartFile details = buildProductDetails();

            MockMultipartFile imageMetadata = buildImageMetadata();

            MockMultipartFile imageFile = buildImageFile();

            MockMultipartFile certMetadata = buildCertificationMetadata();

            MockMultipartFile certFile = buildCertificationFile();

            when(imageStorageService.storeFile(any(), any())).thenReturn(mock(Image.class));
            when(certificationStorageService.storeFile(any(), any())).thenReturn(mock(Certification.class));
            when(productService.saveProduct(any(Product.class))).thenReturn(mock(Product.class));

            mvc.perform(multipart("/api/product")
                            .file(details)
                            .file(imageMetadata)
                            .file(imageFile)
                            .file(certMetadata)
                            .file(certFile))
                    .andExpect(status().isNoContent());

            verify(productService).saveProduct(any(Product.class));
            verify(imageStorageService).storeFile(any(), any());
            verify(certificationStorageService).storeFile(any(), any());
            verify(notificationService).notifyCurators(any(Product.class));
        }

        @Test
        @WithProducerUser
        void shouldReturnBadRequest_whenMetadataAndFilesCountDoNotMatch() throws Exception {
            MockMultipartFile details = buildProductDetails();

            MockMultipartFile imageMetadata = new MockMultipartFile(
                    "imagesMetadata",
                    "",
                    MediaType.APPLICATION_JSON_VALUE,
                    """
                    [
                        {
                            "description": "Immagine prodotto 1"
                        },
                        {
                            "description": "Immagine prodotto 2"
                        }
                    ]
                    """.getBytes()
            );

            MockMultipartFile imageFile = buildImageFile();

            MockMultipartFile certMetadata = buildCertificationMetadata();

            MockMultipartFile certFile = buildCertificationFile();

            mvc.perform(multipart("/api/product")
                            .file(details)
                            .file(imageMetadata)
                            .file(imageFile)
                            .file(certMetadata)
                            .file(certFile))
                    .andExpect(status().isBadRequest());

            verify(productService, never()).saveProduct(any(Product.class));
            verify(notificationService, never()).notifyCurators(any(Product.class));
        }
    }

    private MockMultipartFile buildProductDetails() {
        return new MockMultipartFile(
                "details",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                """
                        {
                            "title": "Pomodori Bio",
                            "description": "Pomodori coltivati biologicamente",
                            "price": 2.99,
                            "quantity": 100
                        }
                        """.getBytes()
        );
    }

    private MockMultipartFile buildImageMetadata() {
        return new MockMultipartFile(
                "imagesMetadata",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                """
                        [
                            {
                                "description": "Immagine prodotto"
                            }
                        ]
                        """.getBytes()
        );
    }

    private MockMultipartFile buildImageFile() {
        return new MockMultipartFile(
                "images",
                "pomodoro.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "immagine di prova".getBytes()
        );
    }

    private MockMultipartFile buildCertificationMetadata() {
        return new MockMultipartFile(
                "certificationsMetadata",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                """
                        [
                            {
                                "certificationType": "BIO"
                            }
                        ]
                        """.getBytes()
        );
    }

    private MockMultipartFile buildCertificationFile() {
        return new MockMultipartFile(
                "certifications",
                "certificato.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "contenuto certificato".getBytes()
        );
    }

}
