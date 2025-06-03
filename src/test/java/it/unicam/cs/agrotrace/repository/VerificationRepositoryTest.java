package it.unicam.cs.agrotrace.repository;

import it.unicam.cs.agrotrace.shared.entity.verification.VerificationEntity;
import it.unicam.cs.agrotrace.util.VerificationTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static it.unicam.cs.agrotrace.util.ContentTestUtils.*;
import static it.unicam.cs.agrotrace.util.UserTestUtils.TEST_CURATOR_ID;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class VerificationRepositoryTest {

    @Autowired
    private VerificationRepository underTest;

    @Test
    void findAllByContentId_shouldReturnEmptyListWhenNoVerificationsExist() {
        UUID contentId = UUID.randomUUID();

        List<VerificationEntity> actual = underTest.findAllByContentId(contentId);

        assertTrue(actual.isEmpty());
    }

    @Test
    void findAllByContentId_shouldReturnListWhenVerificationsExist() {
        VerificationEntity verification = VerificationTestUtils.buildVerificationEntity(null);

        underTest.save(verification);

        List<VerificationEntity> actual = underTest.findAllByContentId(TEST_CONTENT_ID);

        assertFalse(actual.isEmpty());
    }

    @Test
    void findAllByCuratorId_shouldReturnEmptyListWhenNoVerificationsExist() {
        Long curatorId = 1L;

        List<VerificationEntity> actual = underTest.findAllByCuratorId(curatorId);

        assertTrue(actual.isEmpty());
    }

    @Test
    void findAllByCuratorId_shouldReturnListWhenVerificationsExist() {
        VerificationEntity verification = VerificationTestUtils.buildVerificationEntity(null);

        underTest.save(verification);

        List<VerificationEntity> actual = underTest.findAllByCuratorId(TEST_CURATOR_ID);

        assertFalse(actual.isEmpty());
    }
}
