package it.unicam.cs.agrotrace.util.mapper.file;

import it.unicam.cs.agrotrace.rest.request.CertificationMetadataRequest;
import it.unicam.cs.agrotrace.shared.model.file.CertificationDetails;
import it.unicam.cs.agrotrace.shared.entity.file.CertificationEntity;
import it.unicam.cs.agrotrace.shared.model.file.Certification;
import it.unicam.cs.agrotrace.util.mapper.EntityMapper;
import it.unicam.cs.agrotrace.util.mapper.RequestMapper;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class CertificationMapper implements
        EntityMapper<Certification, CertificationEntity>,
        RequestMapper<CertificationMetadataRequest, CertificationDetails> {

    @Override
    public CertificationEntity entityFromModel(Certification model) {
        CertificationEntity entity = new CertificationEntity();
        entity.setId(model.getId());
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

    @Override
    public CertificationDetails modelFromRequest(CertificationMetadataRequest request) {
        return new CertificationDetails(
                Certification.Type.valueOf(request.getCertificationType())
        );
    }
}
