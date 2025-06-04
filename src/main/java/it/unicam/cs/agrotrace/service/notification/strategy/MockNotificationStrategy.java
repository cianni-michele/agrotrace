package it.unicam.cs.agrotrace.service.notification.strategy;

import it.unicam.cs.agrotrace.exception.NotificationErrorException;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class MockNotificationStrategy implements NotificationStrategy {

    private static final Logger logger = LogManager.getLogger(MockNotificationStrategy.class);

    private static final String MESSAGE = """
            Mock notification sent for verification:
            authorEmail: {},
            authorName: {},
            contentTitle: {},
            contentStatus: {},
            comments: {}
            """;

    @Override
    public void sendNotification(Verification verification) throws NotificationErrorException {
        logger.info(MESSAGE,
                verification.authorEmail(),
                verification.authorName(),
                verification.contentTitle(),
                verification.contentStatus(),
                verification.comments()
        );
    }

}
