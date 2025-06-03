package it.unicam.cs.agrotrace.util.mapper.user.author;

import it.unicam.cs.agrotrace.shared.entity.user.author.ProducerEntity;
import it.unicam.cs.agrotrace.shared.model.user.author.Producer;


final class ProducerMapper extends AbstractAuthorMapper<Producer, ProducerEntity> {

    @Override
    public ProducerEntity entityFromModel(Producer model) {
        ProducerEntity entity = new ProducerEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setPassword(model.getPassword());
        entity.setUsername(model.getUsername());
        return entity;
    }

    @Override
    public Producer modelFromEntity(ProducerEntity entity) {
        return Producer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }
}
