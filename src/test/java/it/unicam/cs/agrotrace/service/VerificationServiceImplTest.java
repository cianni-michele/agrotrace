package it.unicam.cs.agrotrace.service;

import it.unicam.cs.agrotrace.exception.ContentNotFoundException;
import it.unicam.cs.agrotrace.exception.CuratorNotFoundException;
import it.unicam.cs.agrotrace.exception.VerificationNotFoundException;
import it.unicam.cs.agrotrace.repository.ContentRepository;
import it.unicam.cs.agrotrace.repository.UserRepository;
import it.unicam.cs.agrotrace.shared.entity.user.CuratorEntity;
import it.unicam.cs.agrotrace.shared.entity.verification.VerificationEntity;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import it.unicam.cs.agrotrace.repository.VerificationRepository;
import it.unicam.cs.agrotrace.util.mapper.user.author.AuthorMapper;
import it.unicam.cs.agrotrace.util.mapper.content.ContentMapper;
import it.unicam.cs.agrotrace.util.mapper.user.admin.CuratorMapper;
import it.unicam.cs.agrotrace.util.mapper.verification.VerificationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static it.unicam.cs.agrotrace.util.AuthorTestUtils.*;
import static it.unicam.cs.agrotrace.util.ContentTestUtils.*;
import static it.unicam.cs.agrotrace.util.UserTestUtils.*;
import static it.unicam.cs.agrotrace.util.VerificationTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerificationServiceImplTest {

    private static final long TEST_VERIFICATION_ID = 1L;

    @Mock
    private VerificationRepository verificationRepository;

    @Mock
    private UserRepository<CuratorEntity> curatorRepository;

    @Mock
    private ContentRepository contentRepository;

    private VerificationServiceImpl verificationService;

    @BeforeEach
    void setUp() {
        AuthorMapper authorMapper = new AuthorMapper();
        ContentMapper contentMapper = new ContentMapper(authorMapper);
        CuratorMapper curatorMapper = new CuratorMapper();
        VerificationMapper verificationMapper = new VerificationMapper(contentMapper, curatorMapper);
        verificationService = new VerificationServiceImpl(
                verificationRepository,
                contentRepository,
                curatorRepository,
                verificationMapper);
    }

    @Nested
    class FindAllByContentIdTests {

        @Test
        void shouldReturnEmptyList_whenNoContentExistsForContentId() {
            UUID contentId = TEST_CONTENT_ID;

            when(verificationRepository.findAllByContentId(contentId)).thenReturn(Collections.emptyList());

            List<Verification> result = verificationService.findAllByContentId(contentId);

            assertTrue(result.isEmpty(), "Expected empty list for content ID");
        }

        @Test
        void shouldReturnListOfVerifications_whenContentIdExists() {
            UUID contentId = TEST_CONTENT_ID;
            VerificationEntity verificationEntity = buildVerificationEntity(TEST_VERIFICATION_ID);
            List<VerificationEntity> verificationEntities = List.of(verificationEntity);

            when(verificationRepository.findAllByContentId(contentId)).thenReturn(verificationEntities);

            List<Verification> actual = verificationService.findAllByContentId(contentId);
            List<Verification> expected = List.of(buildVerificationModel(TEST_VERIFICATION_ID));

            assertEquals(expected, actual);
        }

    }

    @Nested
    class FindAllByCuratorIdTests {

        @Test
        void shouldReturnEmptyList_whenNoVerificationsExistForCuratorId() {
            Long curatorId = TEST_CURATOR_ID;

            when(verificationRepository.findAllByCuratorId(curatorId)).thenReturn(Collections.emptyList());

            List<Verification> result = verificationService.findAllByCuratorId(curatorId);

            assertTrue(result.isEmpty(), "Expected empty list for curator ID");
        }

        @Test
        void shouldReturnListOfVerifications_whenCuratorIdExists() {
            Long curatorId = TEST_CURATOR_ID;
            VerificationEntity verificationEntity = buildVerificationEntity(TEST_VERIFICATION_ID);
            List<VerificationEntity> verificationEntities = List.of(verificationEntity);

            when(verificationRepository.findAllByCuratorId(curatorId)).thenReturn(verificationEntities);

            List<Verification> actual = verificationService.findAllByCuratorId(curatorId);
            List<Verification> expected = List.of(buildVerificationModel(TEST_VERIFICATION_ID));

            assertEquals(expected, actual);
        }
    }

    @Nested
    class CreateVerificationTests {

        @Test
        void shouldThrowException_whenCuratorNotFound() {
            Long curatorId = TEST_CURATOR_ID;

            when(curatorRepository.findById(curatorId)).thenReturn(Optional.empty());

            assertThrows(
                    CuratorNotFoundException.class,
                    () -> verificationService.createVerification(curatorId, TEST_CONTENT_ID, TEST_COMMENT),
                    "Expected CuratorNotFoundException when curator not found"
            );
        }

        @Test
        void shouldThrowException_whenContentNotFound() {
            Long curatorId = TEST_CURATOR_ID;
            UUID contentId = TEST_CONTENT_ID;

            when(curatorRepository.findById(curatorId)).thenReturn(Optional.of(buildTestCuratorEntity(curatorId)));

            when(contentRepository.findById(contentId)).thenReturn(Optional.empty());

            assertThrows(
                    ContentNotFoundException.class,
                    () -> verificationService.createVerification(curatorId, contentId, TEST_COMMENT),
                    "Expected ContentNotFoundException when content not found"
            );
        }

        @Test
        void shouldReturnVerification_whenValidCuratorAndContentProvided() {
            long idVerification = TEST_VERIFICATION_ID;
            Long curatorId = TEST_CURATOR_ID;
            UUID contentId = TEST_CONTENT_ID;

            when(curatorRepository.findById(curatorId))
                    .thenReturn(Optional.of(buildTestCuratorEntity(curatorId)));

            when(contentRepository.findById(contentId))
                    .thenReturn(Optional.of(buildTestProductEntity(contentId, TEST_PRODUCER_ID)));

            when(verificationRepository.save(any(VerificationEntity.class)))
                    .thenReturn(buildVerificationEntity(idVerification));

            Verification actual = verificationService.createVerification(curatorId, contentId, TEST_COMMENT);
            Verification expected = buildVerificationModel(idVerification);

            assertEquals(expected, actual);
        }

    }

    @Nested
    class FindByIdTests {

        @Test
        void shouldThrowException_whenIdNotFound() {
            Long idVerification = TEST_VERIFICATION_ID;

            when(verificationRepository.findById(idVerification)).thenReturn(Optional.empty());

            assertThrows(
                    VerificationNotFoundException.class,
                    () -> verificationService.findById(idVerification),
                    "Expected VerificationNotFoundException when ID not found"
            );
        }

        @Test
        void shouldReturnVerification_whenIdFound() {
            Long idVerification = TEST_VERIFICATION_ID;
            VerificationEntity entity = buildVerificationEntity(idVerification);

            when(verificationRepository.findById(idVerification)).thenReturn(Optional.of(entity));

            Verification actual = verificationService.findById(idVerification);

            Verification expected = buildVerificationModel(idVerification);

            assertEquals(expected, actual, "Expected verification to match the one found by ID");
        }
    }

}
