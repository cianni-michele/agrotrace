package it.unicam.cs.agrotrace.service.notification.strategy;

import it.unicam.cs.agrotrace.exception.NotificationErrorException;
import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.user.User;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;

import java.util.List;

public interface NotificationStrategy {

    /**
     * Sends a notification based on the provided verification.
     *
     * @param verification the verification result to notify
     * @throws NotificationErrorException if an error occurs while sending the notification
     */
    void sendNotification(Verification verification) throws NotificationErrorException;

    /**
     * Sends a notification based on the provided content to a list of users.
     *
     * @param content the content to notify about
     * @param users   the list of users to notify
     * @throws NotificationErrorException if an error occurs while sending the notification
     */
    void sendNotification(Content content, List<? extends User> users) throws NotificationErrorException;
}
