package it.unicam.cs.agrotrace.service.notification.strategy;

import it.unicam.cs.agrotrace.exception.NotificationErrorException;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class EmailNotificationStrategy implements NotificationStrategy {

    private static final Logger logger = LogManager.getLogger(EmailNotificationStrategy.class);

    private final JavaMailSender mailSender;

    public EmailNotificationStrategy(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendNotification(Verification verification) throws NotificationErrorException {
        try {
            logger.info("Sending email notification for verification: {}", verification.id());
            mailSender.send(buildMailMessage(verification));
            logger.info("Email notification sent successfully for verification: {}", verification.id());
        } catch (MailException e) {
            logger.error("Failed to send email notification for verification: {}", verification.id(), e);
            throw new NotificationErrorException("Failed to send email notification: " + e.getMessage(), e);
        }
    }

    private SimpleMailMessage buildMailMessage(Verification verification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(verification.authorEmail());
        message.setSubject("Verification Result for Your Content");
        message.setText("Dear " + verification.authorName() + ",\n\n" +
                        "Your content titled '" + verification.contentTitle() + "' has been verified.\n" +
                        "Verification Result: " + verification.contentStatus() + "\n\n" +
                        "Comments from the curator: " + verification.comments() + "\n\n" +
                        "Thank you for your contribution!\n\n" +
                        "Best regards,\n" +
                        "AgroTrace Team:");
        return message;
    }
}
