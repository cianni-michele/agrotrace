package it.unicam.cs.agrotrace.service.notification;

import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;

public interface NotificationService {

    /**
     * Notify the author of the content about the verification result.
     *
     * @param verification the verification result to notify
     */
    void notifyAuthor(Verification verification);

    /**
     * Notify curators about a new product.
     *
     * @param content the product to notify curators about
     */
    void notifyCurators(Content content);
}
