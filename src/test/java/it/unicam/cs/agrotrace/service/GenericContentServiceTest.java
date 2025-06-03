package it.unicam.cs.agrotrace.service;

import it.unicam.cs.agrotrace.exception.ContentNotFoundException;
import it.unicam.cs.agrotrace.shared.entity.content.ContentEntity;
import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import it.unicam.cs.agrotrace.repository.ContentRepository;
import it.unicam.cs.agrotrace.util.mapper.user.author.AuthorMapper;
import it.unicam.cs.agrotrace.util.mapper.content.ContentMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static it.unicam.cs.agrotrace.shared.model.content.ValidationStatus.PENDING;
import static it.unicam.cs.agrotrace.util.AuthorTestUtils.*;
import static it.unicam.cs.agrotrace.util.ContentTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenericContentServiceTest {

    @Mock
    private ContentRepository contentRepository;

    private GenericContentService contentService;

    @BeforeEach
    void setUp() {
        AuthorMapper authorMapper = new AuthorMapper();
        ContentMapper contentMapper = new ContentMapper(authorMapper);
        contentService = new GenericContentService(contentRepository, contentMapper);
    }

    @Test
    void findById_nullId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> contentService.findById(null));
    }

    @Test
    void findById_nonExistingId_throwsException() {
        UUID contentId = TEST_CONTENT_ID;

        when(contentRepository.findById(contentId)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                ContentNotFoundException.class,
                () -> contentService.findById(contentId),
                "Content with ID " + contentId + " not found"
        );
    }

    @Test
    void findById_existingId_returnsProcess() {
        UUID contentId = TEST_CONTENT_ID;
        long authorId = TEST_TRANSFORMER_ID;

        ContentEntity contentEntity = buildTestProcessEntity(contentId, authorId);

        when(contentRepository.findById(contentId)).thenReturn(Optional.of(contentEntity));

        Content expectedContent = buildTestProcessContent(contentId, authorId, ValidationStatus.PENDING);
        Content actualContent = contentService.findById(contentId);

        assertEquals(expectedContent, actualContent);
    }

    @Test
    void findById_existingId_returnsBundle() {
        UUID contentId = TEST_CONTENT_ID;
        long authorId = TEST_DISTRIBUTOR_ID;

        ContentEntity contentEntity = buildTestBundleEntity(contentId, authorId);

        when(contentRepository.findById(contentId)).thenReturn(Optional.of(contentEntity));

        Content expectedContent = buildTestBundleContent(contentId, authorId, ValidationStatus.PENDING);
        Content actualContent = contentService.findById(contentId);

        assertEquals(expectedContent, actualContent);
    }

    @Test
    void findById_existingId_returnsProduct() {
        UUID contentId = TEST_CONTENT_ID;
        long authorId = TEST_PRODUCER_ID;

        ContentEntity contentEntity = buildTestProductEntity(contentId, authorId);

        when(contentRepository.findById(contentId)).thenReturn(Optional.of(contentEntity));

        Content expectedContent = buildTestProductModel(contentId, authorId, ValidationStatus.PENDING);
        Content actualContent = contentService.findById(contentId);

        assertEquals(expectedContent, actualContent);
    }


    @Test
    void findAll_shouldReturnsEmptyList() {
        ValidationStatus status = PENDING;

        when(contentRepository.findAllByValidationStatus(status.name())).thenReturn(Collections.emptyList());

        List<Content> contents = contentService.findAll(status);

        assertTrue(contents.isEmpty());
    }

    @Test
    void findAll_shouldReturnsListOfContents() {
        ValidationStatus status = PENDING;
        UUID contentId1 = UUID.randomUUID();
        UUID contentId2 = UUID.randomUUID();
        long authorId1 = TEST_TRANSFORMER_ID;
        long authorId2 = TEST_PRODUCER_ID;
        ContentEntity contentEntity1 = buildTestProcessEntity(contentId1, authorId1);
        ContentEntity contentEntity2 = buildTestProductEntity(contentId2, authorId2);

        when(contentRepository.findAllByValidationStatus(status.name())).thenReturn(List.of(contentEntity1, contentEntity2));

        List<Content> expectedContents = List.of(
                buildTestProcessContent(contentId1, authorId1, ValidationStatus.PENDING),
                buildTestProductModel(contentId2, authorId2, ValidationStatus.PENDING)
        );
        List<Content> actualContents = contentService.findAll(status);
        assertEquals(expectedContents, actualContents);
    }

    @Test
    void save_shoulThrowExceptionForNullContent() {
        assertThrows(IllegalArgumentException.class, () -> contentService.save(null));
    }

    @Test
    void save_shouldReturnSavedProcess() {
        Content content = buildTestProcessContent(null, TEST_TRANSFORMER_ID, ValidationStatus.PENDING);
        ContentEntity savedContentEntity = buildTestProcessEntity(TEST_CONTENT_ID, TEST_TRANSFORMER_ID);

        when(contentRepository.save(any(ContentEntity.class))).thenReturn(savedContentEntity);

        Content savedContent = contentService.save(content);

        assertNotNull(savedContent);
        assertEquals(TEST_CONTENT_ID, savedContent.getId());
        assertEquals(content.getAuthor().getId(), savedContent.getAuthor().getId());
        assertEquals(content.getTitle(), savedContent.getTitle());
        assertEquals(content.getDescription(), savedContent.getDescription());
    }

    @Test
    void save_shouldReturnSavedBundle() {
        Content content = buildTestBundleContent(null, TEST_DISTRIBUTOR_ID, ValidationStatus.PENDING);
        ContentEntity savedContentEntity = buildTestBundleEntity(TEST_CONTENT_ID, TEST_DISTRIBUTOR_ID);

        when(contentRepository.save(any(ContentEntity.class))).thenReturn(savedContentEntity);

        Content savedContent = contentService.save(content);

        assertNotNull(savedContent);
        assertEquals(TEST_CONTENT_ID, savedContent.getId());
        assertEquals(content.getAuthor().getId(), savedContent.getAuthor().getId());
        assertEquals(content.getTitle(), savedContent.getTitle());
        assertEquals(content.getDescription(), savedContent.getDescription());
    }

    @Test
    void save_shouldReturnSavedProduct() {
        Content content = buildTestProductModel(null, TEST_PRODUCER_ID, ValidationStatus.PENDING);
        ContentEntity savedContentEntity = buildTestProductEntity(TEST_CONTENT_ID, TEST_PRODUCER_ID);

        when(contentRepository.save(any(ContentEntity.class))).thenReturn(savedContentEntity);

        Content savedContent = contentService.save(content);

        assertNotNull(savedContent);
        assertEquals(TEST_CONTENT_ID, savedContent.getId());
        assertEquals(content.getAuthor().getId(), savedContent.getAuthor().getId());
        assertEquals(content.getTitle(), savedContent.getTitle());
        assertEquals(content.getDescription(), savedContent.getDescription());
    }

}
