package it.unicam.cs.agrotrace.service.notification.strategy;

import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static it.unicam.cs.agrotrace.util.VerificationTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailNotificationStrategyIntegrationTest {

    @Autowired
    private EmailNotificationStrategy emailNotificationStrategy;

    private Verification verification;

    @BeforeEach
    void setUp() {
        verification = buildVerificationModel(1L);
    }

    @Nested
    @ActiveProfiles("dev")
    class DevEmailNotificationStrategyIntegrationTests {

        @Test
        void shouldSendEmailWithoutErrors() {
            assertDoesNotThrow(() -> emailNotificationStrategy.sendNotification(verification));
        }
    }

}
