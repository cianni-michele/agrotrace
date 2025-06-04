package it.unicam.cs.agrotrace.service.notification.strategy;

import it.unicam.cs.agrotrace.exception.NotificationErrorException;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;

public interface NotificationStrategy {

    /**
     * Sends a notification based on the provided verification.
     *
     * @param verification the verification result to notify
     * @throws NotificationErrorException if an error occurs while sending the notification
     */
    void sendNotification(Verification verification) throws NotificationErrorException;
}
