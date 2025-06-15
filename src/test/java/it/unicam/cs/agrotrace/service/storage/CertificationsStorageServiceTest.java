package it.unicam.cs.agrotrace.service.storage;

import it.unicam.cs.agrotrace.service.storage.factory.FileFactory;
import it.unicam.cs.agrotrace.shared.model.file.Certification;
import it.unicam.cs.agrotrace.shared.model.file.CertificationDetails;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("resource")
class CertificationsStorageServiceTest {

    @TempDir
    Path tempDir;

    @Mock
    private FileFactory<Certification, CertificationDetails> fileFactory;

    @Mock
    private Certification mockCertification;

    @Mock
    private CertificationDetails mockDetails;

    private CertificationsStorageService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setProperty("user.dir", tempDir.toString());
        service = new CertificationsStorageService(fileFactory);

        when(fileFactory.createFile(any(Path.class), any(UUID.class), any(CertificationDetails.class)))
                .thenReturn(mockCertification);
    }

    @Test
    void storeFile_shouldStoreFileAndReturnCertification() throws Exception {
        byte[] fileContent = "test file content".getBytes();
        MultipartFile multipartFile = new MockMultipartFile(
                "test.pdf", "test.pdf", "application/pdf", fileContent);

        Certification result = service.storeFile(multipartFile, mockDetails);

        assertEquals(mockCertification, result, "Stored certification should match the mock certification");
        verify(fileFactory).createFile(any(Path.class), any(UUID.class), eq(mockDetails));

        // Usa la posizione effettiva di archiviazione del servizio
        Path certDir = service.getFileStorageLocation();
        assertTrue(Files.exists(certDir), "Certifications directory should exist");

        assertTrue(Files.list(certDir).findAny().isPresent(), "Certification file should be created");

        Path savedFile = Files.list(certDir).findFirst().orElseThrow();
        byte[] savedContent = Files.readAllBytes(savedFile);
        assertArrayEquals(fileContent, savedContent);
    }
}