package it.unicam.cs.agrotrace.service.notification.strategy;

import it.unicam.cs.agrotrace.exception.NotificationErrorException;
import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.user.User;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;


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

    @Override
    public void sendNotification(Content content, List<? extends User> users) throws NotificationErrorException {
        for (User user : users) {
            try {
                logger.info("Sending email notification for content: {}", content.getId());
                logger.info("User to notify: {}", user.getEmail() + "with ID: " + user.getId());
                mailSender.send(buildMailMessage(content, user));
                logger.info("Email notification sent successfully for content: {}", content.getId());
            } catch (MailException e) {
                logger.error("Failed to send email notification for content: {}", content.getId(), e);
                throw new NotificationErrorException("Failed to send email notification: " + e.getMessage(), e);
            }
        }
    }

    private SimpleMailMessage buildMailMessage(Content content, User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("New Content Available: " + content.getTitle());
        message.setText("Dear " + user.getName() + ",\n\n" +
                        "New content titled '" + content.getTitle() + "' has been added.\n" +
                        "Best regards,\n" +
                        "AgroTrace Team");
        return message;
    }
}
