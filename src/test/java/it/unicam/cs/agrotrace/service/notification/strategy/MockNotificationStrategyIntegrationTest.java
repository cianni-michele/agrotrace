package it.unicam.cs.agrotrace.service.notification.strategy;

import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static it.unicam.cs.agrotrace.util.VerificationTestUtils.buildVerificationModel;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class MockNotificationStrategyIntegrationTest {

    @Autowired
    private MockNotificationStrategy mockNotificationStrategy;

    private Verification verification;

    @BeforeEach
    void setUp() {
        verification = buildVerificationModel(1L);
    }

    @Nested
    @ActiveProfiles("dev")
    class DevMockNotificationStrategyIntegrationTests {

        @Test
        void shouldLogMockNotificationWithoutErrors() {
            assertDoesNotThrow(() -> mockNotificationStrategy.sendNotification(verification));
        }
    }
}
