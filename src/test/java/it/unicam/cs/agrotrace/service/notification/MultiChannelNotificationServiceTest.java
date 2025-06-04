package it.unicam.cs.agrotrace.service.notification;

import it.unicam.cs.agrotrace.exception.NotificationErrorException;
import it.unicam.cs.agrotrace.service.notification.strategy.NotificationStrategy;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MultiChannelNotificationServiceTest {

    private static final NotificationErrorException ERROR = new NotificationErrorException(
            "Test error",
            new RuntimeException()
    );

    @Mock
    private NotificationStrategy successStrategy;

    @Mock
    private NotificationStrategy failureStrategy;

    @Mock
    private Verification mockVerification;

    private MultiChannelNotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new MultiChannelNotificationService(List.of(successStrategy, failureStrategy));
    }

    @Test
    void shouldSendNotificationWithAllStrategies() {
        doNothing().when(successStrategy).sendNotification(mockVerification);
        doNothing().when(failureStrategy).sendNotification(mockVerification);

        notificationService.notifyAuthor(mockVerification);

        verify(successStrategy).sendNotification(mockVerification);
        verify(failureStrategy).sendNotification(mockVerification);
    }

    @Test
    void shouldSendNotificationOnlyWithSuccessStrategy() {
        doNothing().when(successStrategy).sendNotification(mockVerification);
        doThrow(ERROR).when(failureStrategy).sendNotification(mockVerification);

        notificationService.notifyAuthor(mockVerification);

        verify(successStrategy).sendNotification(mockVerification);
        verify(failureStrategy).sendNotification(mockVerification);
    }

    @Test
    void shouldNotSendNotificationIfAllStrategiesFail() {
        doThrow(ERROR).when(successStrategy).sendNotification(mockVerification);
        doThrow(ERROR).when(failureStrategy).sendNotification(mockVerification);

        notificationService.notifyAuthor(mockVerification);

        verify(successStrategy).sendNotification(mockVerification);
        verify(failureStrategy).sendNotification(mockVerification);
    }
}
