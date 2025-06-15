package it.unicam.cs.agrotrace.service.notification.strategy;

import it.unicam.cs.agrotrace.exception.NotificationErrorException;
import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.user.User;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("dev")
@Component
public class MockNotificationStrategy implements NotificationStrategy {

    private static final Logger logger = LogManager.getLogger(MockNotificationStrategy.class);

    @Override
    public void sendNotification(Verification verification) throws NotificationErrorException {
        logger.info(getVerificationMessage(),
                verification.authorEmail(),
                verification.authorName(),
                verification.contentTitle(),
                verification.contentStatus(),
                verification.comments()
        );
    }

    private String getVerificationMessage() {
        return """
                Mock notification sent for verification:
                authorEmail: {},
                authorName: {},
                contentTitle: {},
                contentStatus: {},
                comments: {}
                """;
    }

    @Override
    public void sendNotification(Content content, List<? extends User> users) throws NotificationErrorException {
        for (User user : users) {
            logger.info(getContentMessage(),
                    content.getId(),
                    user.getEmail(),
                    user.getId(),
                    content.getId(),
                    content.getTitle()
            );
        }
    }

    private String getContentMessage() {
        return """
                Mock notification sent for content: {}
                User to notify: {} with ID: {}
                contentId: {}
                contentTitle: {}
                """;
    }


}
