package it.unicam.cs.agrotrace.repository;

import it.unicam.cs.agrotrace.shared.entity.content.BundleEntity;
import it.unicam.cs.agrotrace.shared.entity.content.ContentEntity;
import it.unicam.cs.agrotrace.shared.entity.content.ProcessEntity;
import it.unicam.cs.agrotrace.shared.entity.content.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static it.unicam.cs.agrotrace.util.AuthorTestUtils.*;
import static it.unicam.cs.agrotrace.util.ContentTestUtils.*;
import static it.unicam.cs.agrotrace.util.UploadedFileTestUtils.TEST_FILE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ContentRepositoryTest {

    @Autowired
    private ContentRepository contentRepository;


    @Test
    void saveAndfindById_shouldPersistProcessEntity() {
        ContentEntity expected = buildTestProcessEntity(null, TEST_TRANSFORMER_ID);

        expected = contentRepository.save(expected);

        Optional<ContentEntity> contentFound = contentRepository.findById(expected.getId());

        assertTrue(contentFound.isPresent());
        assertEquals(expected, contentFound.get());
    }

    @Test
    void saveAndFindById_shouldPersistProductEntity() {
        ContentEntity expected = buildTestProductEntity(null, TEST_PRODUCER_ID, TEST_FILE_ID, TEST_FILE_ID);

        expected = contentRepository.save(expected);

        Optional<ContentEntity> contentFound = contentRepository.findById(expected.getId());

        assertTrue(contentFound.isPresent());
        assertEquals(expected, contentFound.get());
    }

    @Test
    void saveAndFindById_shouldPersistBundleEntity() {
        ContentEntity expected = buildTestBundleEntity(null, TEST_DISTRIBUTOR_ID);

        expected = contentRepository.save(expected);

        Optional<ContentEntity> contentFound = contentRepository.findById(expected.getId());

        assertTrue(contentFound.isPresent());
        assertEquals(expected, contentFound.get());
    }

    @Test
    void findAll_shouldReturnsAllContentEntities() {
        List<ContentEntity> contentEntities = contentRepository.findAll();

        assertEquals(3, contentEntities.size());
        assertTrue(contentEntities.stream().anyMatch(entity -> entity instanceof ProcessEntity));
        assertTrue(contentEntities.stream().anyMatch(entity -> entity instanceof ProductEntity));
        assertTrue(contentEntities.stream().anyMatch(entity -> entity instanceof BundleEntity));
    }

    @Test
    void deleteById_shouldRemoveProductEntityFromDatabase() {
        ContentEntity contentEntity = buildTestProductEntity(null, TEST_PRODUCER_ID, TEST_FILE_ID, TEST_FILE_ID);

        contentEntity = contentRepository.save(contentEntity);

        contentRepository.deleteById(contentEntity.getId());

        Optional<ContentEntity> contentFound = contentRepository.findById(contentEntity.getId());
        assertTrue(contentFound.isEmpty(), "Product entity should be deleted from the database");

    }

    @Test
    void deleteById_shouldRemoveProcessEntityFromDatabase() {
        ContentEntity contentEntity = buildTestProcessEntity(null, TEST_TRANSFORMER_ID);

        contentEntity = contentRepository.save(contentEntity);

        contentRepository.deleteById(contentEntity.getId());

        Optional<ContentEntity> contentFound = contentRepository.findById(contentEntity.getId());
        assertTrue(contentFound.isEmpty(), "Process entity should be deleted from the database");
    }

    @Test
    void deleteById_shouldRemoveBundleEntityFromDatabase() {
        ContentEntity contentEntity = buildTestBundleEntity(null, TEST_DISTRIBUTOR_ID);

        contentEntity = contentRepository.save(contentEntity);

        contentRepository.deleteById(contentEntity.getId());

        Optional<ContentEntity> contentFound = contentRepository.findById(contentEntity.getId());
        assertTrue(contentFound.isEmpty(), "Bundle entity should be deleted from the database");
    }

    @Test
    void update_shouldUpdateProcessEntity() {
        ContentEntity contentEntity = buildTestProcessEntity(null, TEST_TRANSFORMER_ID);
        contentEntity = contentRepository.save(contentEntity);

        contentEntity.setValidationStatus(TEST_CONTENT_APPROVED);
        contentEntity = contentRepository.save(contentEntity);

        Optional<ContentEntity> contentFound = contentRepository.findById(contentEntity.getId());
        assertTrue(contentFound.isPresent(), "Process entity should be found in the database");
        assertEquals(TEST_CONTENT_APPROVED, contentFound.get().getValidationStatus(), "Process entity validation status should be updated");
    }

    @Test
    void update_shouldUpdateProductEntity() {
        ContentEntity contentEntity = buildTestProductEntity(null, TEST_PRODUCER_ID, null, null);
        contentEntity = contentRepository.save(contentEntity);

        contentEntity.setValidationStatus(TEST_CONTENT_REJECTED);
        contentEntity = contentRepository.save(contentEntity);

        Optional<ContentEntity> contentFound = contentRepository.findById(contentEntity.getId());
        assertTrue(contentFound.isPresent(), "Product entity should be found in the database");
        assertEquals(TEST_CONTENT_REJECTED, contentFound.get().getValidationStatus(), "Product entity validation status should be updated");
    }

    @Test
    void update_shouldUpdateBundleEntity() {
        ContentEntity contentEntity = buildTestBundleEntity(null, TEST_DISTRIBUTOR_ID);
        contentEntity = contentRepository.save(contentEntity);

        contentEntity.setValidationStatus("PENDING");
        contentEntity = contentRepository.save(contentEntity);

        Optional<ContentEntity> contentFound = contentRepository.findById(contentEntity.getId());
        assertTrue(contentFound.isPresent(), "Bundle entity should be found in the database");
        assertEquals("PENDING", contentFound.get().getValidationStatus(), "Bundle entity validation status should be updated");
    }
}
