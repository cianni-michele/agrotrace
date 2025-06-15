package it.unicam.cs.agrotrace.service.notification.strategy;

import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.content.Product;
import it.unicam.cs.agrotrace.shared.model.user.User;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import it.unicam.cs.agrotrace.util.AuthorTestUtils;
import it.unicam.cs.agrotrace.util.ContentTestUtils;
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
import static it.unicam.cs.agrotrace.util.ContentTestUtils.*;
import static it.unicam.cs.agrotrace.util.VerificationTestUtils.buildVerificationModel;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class MockNotificationStrategyIntegrationTest {

    @Autowired
    private MockNotificationStrategy mockNotificationStrategy;

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
    class DevMockNotificationStrategyIntegrationTests {

        @Test
        void shouldLogMockNotificationForVerification() {
            assertDoesNotThrow(() -> mockNotificationStrategy.sendNotification(verification));
        }

        @Test
        void shouldLogMockNotificationForContent() {
            assertDoesNotThrow(() -> mockNotificationStrategy.sendNotification(content, users));
        }

    }
}
