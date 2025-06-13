package it.unicam.cs.agrotrace.util;

import it.unicam.cs.agrotrace.shared.entity.verification.VerificationEntity;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;

import static it.unicam.cs.agrotrace.util.AuthorTestUtils.TEST_PRODUCER_ID;
import static it.unicam.cs.agrotrace.util.ContentTestUtils.*;
import static it.unicam.cs.agrotrace.util.UserTestUtils.*;

public final class VerificationTestUtils {

    public static final String TEST_COMMENT = "Test comment";

    public static VerificationEntity buildVerificationEntity(Long idVerification) {
        VerificationEntity verificationEntity = new VerificationEntity();
        verificationEntity.setId(idVerification);
        verificationEntity.setContent(buildTestProductEntity(TEST_CONTENT_ID, TEST_PRODUCER_ID));
        verificationEntity.setCurator(buildTestCuratorEntity(TEST_CURATOR_ID));
        verificationEntity.setComments(TEST_COMMENT);
        return verificationEntity;
    }

    public static Verification buildVerificationModel(Long idVerification) {
        return Verification.builder()
                .id(idVerification)
                .content(buildTestProductContent(TEST_CONTENT_ID, TEST_PRODUCER_ID, ValidationStatus.PENDING))
                .curator(buildTestCuratorModel(TEST_CURATOR_ID))
                .comments(TEST_COMMENT)
                .build();
    }
}
