package it.unicam.cs.agrotrace.config.mail;

import it.unicam.cs.agrotrace.service.notification.strategy.EmailNotificationStrategy;
import it.unicam.cs.agrotrace.service.notification.strategy.MockNotificationStrategy;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class NotificationStrategyProfilesInjectionIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Nested
    @ActiveProfiles("dev")
    class DevProfileTest {
        @Test
        void shouldLoadMockStrategy() {
            assertNotNull(context.getBean(MockNotificationStrategy.class));
        }

        @Test
        void shouldLoadEmailStrategy() {
            assertNotNull(context.getBean(EmailNotificationStrategy.class));
        }
    }

}
