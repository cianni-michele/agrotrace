package it.unicam.cs.agrotrace.service.notification.strategy;

import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.user.User;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import it.unicam.cs.agrotrace.util.UserTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static it.unicam.cs.agrotrace.shared.model.content.ValidationStatus.PENDING;
import static it.unicam.cs.agrotrace.util.AuthorTestUtils.TEST_PRODUCER_ID;
import static it.unicam.cs.agrotrace.util.ContentTestUtils.TEST_CONTENT_ID;
import static it.unicam.cs.agrotrace.util.ContentTestUtils.buildTestProductContent;
import static it.unicam.cs.agrotrace.util.VerificationTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailNotificationStrategyIntegrationTest {

    @Autowired
    private EmailNotificationStrategy emailNotificationStrategy;

    private Verification verification;

    private Content content;

    private List<? extends User> users;

    @BeforeEach
    void setUp() {
        verification = buildVerificationModel(1L);
        content = buildTestProductContent(TEST_CONTENT_ID, TEST_PRODUCER_ID, PENDING);
        users = List.of(
                UserTestUtils.buildTestCuratorModel(1L)
        );
    }

    @Nested
    @ActiveProfiles("dev")
    class DevEmailNotificationStrategyIntegrationTests {

        @Test
        void shouldSendEmailForVerification() {
            assertDoesNotThrow(() -> emailNotificationStrategy.sendNotification(verification));
        }

        @Test
        void shouldSendEmailForContent() {
            assertDoesNotThrow(() -> emailNotificationStrategy.sendNotification(content, users));
        }
    }

}
