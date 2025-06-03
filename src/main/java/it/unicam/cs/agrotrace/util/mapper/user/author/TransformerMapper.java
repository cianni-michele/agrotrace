package it.unicam.cs.agrotrace.util.mapper.user.author;

import it.unicam.cs.agrotrace.shared.entity.user.author.TransformerEntity;
import it.unicam.cs.agrotrace.shared.model.user.author.Transformer;

final class TransformerMapper extends AbstractAuthorMapper<Transformer, TransformerEntity> {
    
    @Override
    public TransformerEntity entityFromModel(Transformer model) {
        TransformerEntity entity = new TransformerEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setPassword(model.getPassword());
        entity.setUsername(model.getUsername());
        return entity;
    }

    @Override
    public Transformer modelFromEntity(TransformerEntity entity) {
        return Transformer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }
}
