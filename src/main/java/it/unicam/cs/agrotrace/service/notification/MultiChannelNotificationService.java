package it.unicam.cs.agrotrace.service.notification;

import it.unicam.cs.agrotrace.exception.NotificationErrorException;
import it.unicam.cs.agrotrace.repository.UserRepository;
import it.unicam.cs.agrotrace.service.notification.strategy.NotificationStrategy;
import it.unicam.cs.agrotrace.shared.entity.user.CuratorEntity;
import it.unicam.cs.agrotrace.shared.entity.user.UserEntity;
import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.user.admin.Curator;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import it.unicam.cs.agrotrace.util.mapper.user.admin.CuratorMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class MultiChannelNotificationService implements NotificationService {

    private static final Logger logger = LogManager.getLogger(MultiChannelNotificationService.class);
    private static final String ERROR_MESSAGE = "Failed to send notification using strategy: {}";

    private final List<NotificationStrategy> notificationStrategies;
    private final UserRepository<CuratorEntity> curatorRepository;
    private final CuratorMapper curatorMapper;

    public MultiChannelNotificationService(List<NotificationStrategy> notificationStrategies,
                                           UserRepository<CuratorEntity> curatorRepository,
                                           CuratorMapper curatorMapper) {
        this.notificationStrategies = notificationStrategies;
        this.curatorRepository = curatorRepository;
        this.curatorMapper = curatorMapper;
    }

    @Override
    public void notifyAuthor(Verification verification) {
        notificationStrategies.forEach(strategy -> {
            try {
                strategy.sendNotification(verification);
            } catch (NotificationErrorException e) {
                logger.error(ERROR_MESSAGE, strategy.getClass().getSimpleName(), e);
            }
        });
    }

    @Override
    public void notifyCurators(Content content) {
        Iterable<? extends UserEntity> curatorEntities = curatorRepository.findAll();

        List<Curator> curators = StreamSupport.stream(curatorEntities.spliterator(), false)
                .filter(CuratorEntity.class::isInstance)
                .map(CuratorEntity.class::cast)
                .map(curatorMapper::modelFromEntity)
                .toList();

        notificationStrategies.forEach(strategy -> {
            try {
                strategy.sendNotification(content, curators);
            } catch (NotificationErrorException e) {
                logger.error(ERROR_MESSAGE, strategy.getClass().getSimpleName(), e);
            }
        });
    }
}
