package it.unicam.cs.agrotrace.service.notification;

import it.unicam.cs.agrotrace.exception.NotificationErrorException;
import it.unicam.cs.agrotrace.repository.UserRepository;
import it.unicam.cs.agrotrace.service.notification.strategy.NotificationStrategy;
import it.unicam.cs.agrotrace.shared.entity.user.CuratorEntity;
import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import it.unicam.cs.agrotrace.util.mapper.user.admin.CuratorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
    private UserRepository<CuratorEntity> curatorRepository;

    private MultiChannelNotificationService notificationService;

    @BeforeEach
    void setUp() {
        CuratorMapper curatorMapper = new CuratorMapper();

        notificationService = new MultiChannelNotificationService(
                List.of(successStrategy, failureStrategy),
                curatorRepository,
                curatorMapper
        );
    }

    @Nested
    class NotifyCuratorsTests {

        @Test
        void shouldSendNotificationWithAllStrategies() {
            doNothing().when(successStrategy).sendNotification(any(), any());
            doNothing().when(failureStrategy).sendNotification(any(), any());

            when(curatorRepository.findAll()).thenReturn(List.of(mock(CuratorEntity.class)));

            notificationService.notifyCurators(mock(Content.class));

            verify(successStrategy).sendNotification(any(), any());
            verify(failureStrategy).sendNotification(any(), any());
        }

        @Test
        void shouldSendNotificationOnlyWithSuccessStrategy() {
            doNothing().when(successStrategy).sendNotification(any(), any());
            doThrow(ERROR).when(failureStrategy).sendNotification(any(), any());

            when(curatorRepository.findAll()).thenReturn(List.of(mock(CuratorEntity.class)));

            notificationService.notifyCurators(mock(Content.class));

            verify(successStrategy).sendNotification(any(), any());
            verify(failureStrategy).sendNotification(any(), any());
        }

        @Test
        void shouldNotSendNotificationIfAllStrategiesFail() {
            doThrow(ERROR).when(successStrategy).sendNotification(any(), any());
            doThrow(ERROR).when(failureStrategy).sendNotification(any(), any());

            when(curatorRepository.findAll()).thenReturn(List.of(mock(CuratorEntity.class)));

            notificationService.notifyCurators(mock(Content.class));

            verify(successStrategy).sendNotification(any(), any());
            verify(failureStrategy).sendNotification(any(), any());

        }
    }

    @Nested
    class NotifyAuthorTests {

        @Mock
        private Verification mockVerification;

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

}
