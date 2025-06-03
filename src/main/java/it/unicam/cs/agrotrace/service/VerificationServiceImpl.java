package it.unicam.cs.agrotrace.service;

import it.unicam.cs.agrotrace.exception.ContentNotFoundException;
import it.unicam.cs.agrotrace.exception.CuratorNotFoundException;
import it.unicam.cs.agrotrace.exception.VerificationNotFoundException;
import it.unicam.cs.agrotrace.repository.ContentRepository;
import it.unicam.cs.agrotrace.repository.UserRepository;
import it.unicam.cs.agrotrace.shared.entity.content.ContentEntity;
import it.unicam.cs.agrotrace.shared.entity.user.CuratorEntity;
import it.unicam.cs.agrotrace.shared.entity.verification.VerificationEntity;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import it.unicam.cs.agrotrace.repository.VerificationRepository;
import it.unicam.cs.agrotrace.util.mapper.verification.VerificationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VerificationServiceImpl implements VerificationService {

    private final VerificationRepository verificationRepository;

    private final ContentRepository contentRepository;

    private final UserRepository<CuratorEntity> curatorRepository;

    private final VerificationMapper verificationMapper;

    public VerificationServiceImpl(VerificationRepository verificationRepository,
                                   ContentRepository contentRepository,
                                   UserRepository<CuratorEntity> curatorRepository,
                                   VerificationMapper verificationMapper) {
        this.verificationRepository = verificationRepository;
        this.contentRepository = contentRepository;
        this.curatorRepository = curatorRepository;
        this.verificationMapper = verificationMapper;
    }

    @Override
    public Verification createVerification(Long curatorId, UUID contentId, String comments) {
        CuratorEntity curatorEntity = curatorRepository.findById(curatorId).
                orElseThrow(() -> new CuratorNotFoundException(curatorId));

        ContentEntity contentEntity = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotFoundException(contentId));

        VerificationEntity entity = new VerificationEntity();
        entity.setContent(contentEntity);
        entity.setCurator(curatorEntity);
        entity.setComments(comments);

        VerificationEntity savedEntity = verificationRepository.save(entity);

        return verificationMapper.modelFromEntity(savedEntity);
    }

    @Override
    public Verification findById(Long id) {
        return verificationRepository.findById(id)
                .map(verificationMapper::modelFromEntity)
                .orElseThrow(() -> new VerificationNotFoundException(id));
    }

    @Override
    public List<Verification> findAllByCuratorId(Long curatorId) {
        return verificationRepository.findAllByCuratorId(curatorId)
                .stream()
                .map(verificationMapper::modelFromEntity)
                .toList();
    }

    @Override
    public List<Verification> findAllByContentId(UUID contentId) {
        return verificationRepository.findAllByContentId(contentId)
                .stream()
                .map(verificationMapper::modelFromEntity)
                .toList();
    }

}
