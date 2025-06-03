package it.unicam.cs.agrotrace.util.mapper.user.admin;

import it.unicam.cs.agrotrace.shared.entity.user.CuratorEntity;
import it.unicam.cs.agrotrace.shared.model.user.admin.Curator;
import it.unicam.cs.agrotrace.util.mapper.RepositoryMapper;
import org.springframework.stereotype.Component;

@Component
public class CuratorMapper implements RepositoryMapper<Curator, CuratorEntity> {

    @Override
    public CuratorEntity entityFromModel(Curator model) {
        CuratorEntity entity = new CuratorEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setUsername(model.getUsername());
        entity.setPassword(model.getPassword());
        return entity;
    }

    @Override
    public Curator modelFromEntity(CuratorEntity entity) {
        return Curator.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }
}
