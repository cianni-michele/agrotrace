package it.unicam.cs.agrotrace.util.mapper.user.author;

import it.unicam.cs.agrotrace.shared.entity.user.author.AuthorEntity;
import it.unicam.cs.agrotrace.shared.entity.user.author.DistributorEntity;
import it.unicam.cs.agrotrace.shared.entity.user.author.ProducerEntity;
import it.unicam.cs.agrotrace.shared.entity.user.author.TransformerEntity;
import it.unicam.cs.agrotrace.shared.model.user.author.Author;
import it.unicam.cs.agrotrace.shared.model.user.author.Distributor;
import it.unicam.cs.agrotrace.shared.model.user.author.Producer;
import it.unicam.cs.agrotrace.shared.model.user.author.Transformer;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper extends AbstractAuthorMapper<Author, AuthorEntity> {

    private final ProducerMapper producerMapper;

    private final TransformerMapper transformerMapper;

    private final DistributorMapper distributorMapper;

    public AuthorMapper() {
        producerMapper = new ProducerMapper();
        transformerMapper = new TransformerMapper();
        distributorMapper = new DistributorMapper();
    }


    @Override
    public AuthorEntity entityFromModel(Author model) {
        return switch (model) {
            case Producer producer -> producerMapper.entityFromModel(producer);
            case Distributor distributor -> distributorMapper.entityFromModel(distributor);
            case Transformer transformer -> transformerMapper.entityFromModel(transformer);
            default ->
                    throw new IllegalArgumentException("Unsupported author model type: " + model.getClass().getSimpleName());
        };
    }


    @Override
    public Author modelFromEntity(AuthorEntity entity) {
        return switch (entity) {
            case ProducerEntity producerEntity -> producerMapper.modelFromEntity(producerEntity);
            case DistributorEntity distributorEntity -> distributorMapper.modelFromEntity(distributorEntity);
            case TransformerEntity transformerEntity -> transformerMapper.modelFromEntity(transformerEntity);
            default ->
                    throw new IllegalArgumentException("Unsupported author entity type: " + entity.getClass().getSimpleName());
        };
    }
}
