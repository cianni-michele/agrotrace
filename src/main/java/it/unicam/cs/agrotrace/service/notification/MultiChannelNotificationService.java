package it.unicam.cs.agrotrace.service.notification;

import it.unicam.cs.agrotrace.exception.NotificationErrorException;
import it.unicam.cs.agrotrace.service.notification.strategy.NotificationStrategy;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MultiChannelNotificationService implements NotificationService {

    private static final Logger logger = LogManager.getLogger(MultiChannelNotificationService.class);

    private final List<NotificationStrategy> notificationStrategies;

    public MultiChannelNotificationService(List<NotificationStrategy> notificationStrategies) {
        this.notificationStrategies = notificationStrategies;
    }

    @Override
    public void notifyAuthor(Verification verification) {
        notificationStrategies.forEach(strategy -> {
            try {
                strategy.sendNotification(verification);
            } catch (NotificationErrorException e) {
                logger.error("Failed to send notification using strategy: {}", strategy.getClass().getSimpleName(), e);
            }
        });
    }
}
