package it.unicam.cs.agrotrace.util.mapper.verification;

import it.unicam.cs.agrotrace.shared.entity.verification.VerificationEntity;
import it.unicam.cs.agrotrace.shared.model.verification.Verification;
import it.unicam.cs.agrotrace.util.mapper.RepositoryMapper;
import it.unicam.cs.agrotrace.util.mapper.content.ContentMapper;
import it.unicam.cs.agrotrace.util.mapper.user.admin.CuratorMapper;
import org.springframework.stereotype.Component;

@Component
public class VerificationMapper implements RepositoryMapper<Verification, VerificationEntity> {

    private final ContentMapper contentMapper;

    private final CuratorMapper curatorMapper;

    public VerificationMapper(ContentMapper contentMapper, CuratorMapper curatorMapper) {
        this.contentMapper = contentMapper;
        this.curatorMapper = curatorMapper;
    }

    @Override
    public VerificationEntity entityFromModel(Verification model) {
        VerificationEntity entity = new VerificationEntity();
        entity.setId(model.id());
        entity.setCurator(curatorMapper.entityFromModel(model.curator()));
        entity.setContent(contentMapper.entityFromModel(model.content()));
        entity.setComments(model.comments());
        return entity;
    }

    @Override
    public Verification modelFromEntity(VerificationEntity entity) {
        return Verification.builder()
                .id(entity.getId())
                .curator(curatorMapper.modelFromEntity(entity.getCurator()))
                .content(contentMapper.modelFromEntity(entity.getContent()))
                .comments(entity.getComments())
                .build();
    }
}
