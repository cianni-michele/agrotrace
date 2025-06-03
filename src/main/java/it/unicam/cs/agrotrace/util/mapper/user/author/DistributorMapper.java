package it.unicam.cs.agrotrace.util.mapper.user.author;

import it.unicam.cs.agrotrace.shared.entity.user.author.DistributorEntity;
import it.unicam.cs.agrotrace.shared.model.user.author.Distributor;


final class DistributorMapper extends AbstractAuthorMapper<Distributor, DistributorEntity> {

    @Override
    public DistributorEntity entityFromModel(Distributor model) {
        DistributorEntity entity = new DistributorEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setPassword(model.getPassword());
        entity.setUsername(model.getUsername());
        return entity;
    }

    @Override
    public Distributor modelFromEntity(DistributorEntity entity) {
        return Distributor.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }
}
