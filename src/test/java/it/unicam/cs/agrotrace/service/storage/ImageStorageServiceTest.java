package it.unicam.cs.agrotrace.service.storage;

import it.unicam.cs.agrotrace.service.storage.factory.FileFactory;
import it.unicam.cs.agrotrace.shared.model.file.Image;
import it.unicam.cs.agrotrace.shared.model.file.ImageDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("resource")
class ImageStorageServiceTest {

    @TempDir
    Path tempDir;

    @Mock
    private FileFactory<Image, ImageDetails> fileFactory;

    @Mock
    private Image mockImage;

    @Mock
    private ImageDetails mockDetails;

    private ImageStorageService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setProperty("user.dir", tempDir.toString());
        service = new ImageStorageService(fileFactory);

        when(fileFactory.createFile(any(Path.class), any(UUID.class), any(ImageDetails.class)))
                .thenReturn(mockImage);
    }

    @Test
    void storeFile_shouldStoreFileAndReturnImage() throws Exception {
        byte[] fileContent = "test image content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile(
                "test.jpg", "test.jpg", "image/jpeg", fileContent);

        Image result = service.storeFile(multipartFile, mockDetails);

        assertEquals(mockImage, result, "Stored image should match the mock image");
        verify(fileFactory).createFile(any(Path.class), any(UUID.class), eq(mockDetails));

        // Usa la posizione effettiva di archiviazione del servizio
        Path imageDir = service.getFileStorageLocation();
        assertTrue(Files.exists(imageDir), "Image directory should exist");

        assertTrue(Files.list(imageDir).findAny().isPresent(), "Image directory should contain at least one file");

        Path savedFile = Files.list(imageDir).findFirst().orElseThrow();
        byte[] savedContent = Files.readAllBytes(savedFile);
        assertArrayEquals(fileContent, savedContent);
    }
}