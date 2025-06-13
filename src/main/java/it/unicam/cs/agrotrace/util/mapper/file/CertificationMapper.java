package it.unicam.cs.agrotrace.util.mapper.file;

import it.unicam.cs.agrotrace.shared.entity.file.CertificationEntity;
import it.unicam.cs.agrotrace.shared.model.file.Certification;
import it.unicam.cs.agrotrace.util.mapper.RepositoryMapper;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class CertificationMapper implements RepositoryMapper<Certification, CertificationEntity> {

    @Override
    public CertificationEntity entityFromModel(Certification model) {
        CertificationEntity entity = new CertificationEntity();
        entity.setPath(model.getPath().toString());
        entity.setType(model.getType().name());
        return entity;
    }

    @Override
    public Certification modelFromEntity(CertificationEntity entity) {
        return Certification.builder()
                .id(entity.getId())
                .type(Certification.Type.valueOf(entity.getType()))
                .path(Path.of(entity.getPath()))
                .build();
    }
}
