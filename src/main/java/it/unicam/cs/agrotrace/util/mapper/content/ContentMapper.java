package it.unicam.cs.agrotrace.util.mapper.content;

import it.unicam.cs.agrotrace.rest.view.ContentView;
import it.unicam.cs.agrotrace.shared.entity.content.BundleEntity;
import it.unicam.cs.agrotrace.shared.entity.content.ContentEntity;
import it.unicam.cs.agrotrace.shared.entity.content.ProcessEntity;
import it.unicam.cs.agrotrace.shared.entity.content.ProductEntity;
import it.unicam.cs.agrotrace.shared.model.content.*;
import it.unicam.cs.agrotrace.shared.model.content.Process;
import it.unicam.cs.agrotrace.util.mapper.RepositoryMapper;
import it.unicam.cs.agrotrace.util.mapper.RestMapper;
import it.unicam.cs.agrotrace.util.mapper.user.author.AuthorMapper;
import org.springframework.stereotype.Component;

@Component
public class ContentMapper implements RestMapper<ContentView, Content>, RepositoryMapper<Content, ContentEntity> {

    private final ProcessMapper processMapper;

    private final ProductMapper productMapper;

    private final BundleMapper bundleMapper;

    public ContentMapper(AuthorMapper authorMapper) {
        this.processMapper = new ProcessMapper(authorMapper);
        this.productMapper = new ProductMapper(authorMapper);
        this.bundleMapper = new BundleMapper(authorMapper);
    }

    @Override
    public Content modelFromEntity(ContentEntity entity) {
        return switch (entity) {
            case ProcessEntity processEntity -> processMapper.modelFromEntity(processEntity);
            case ProductEntity productEntity -> productMapper.modelFromEntity(productEntity);
            case BundleEntity bundleEntity -> bundleMapper.modelFromEntity(bundleEntity);
            default ->
                    throw new IllegalArgumentException("Unknown content entity type: " + entity.getClass().getSimpleName());
        };
    }

    @Override
    public ContentEntity entityFromModel(Content model) {
        return switch (model) {
            case Process process -> processMapper.entityFromModel(process);
            case Product product -> productMapper.entityFromModel(product);
            case Bundle bundle -> bundleMapper.entityFromModel(bundle);
            default ->
                    throw new IllegalArgumentException("Unknown content model type: " + model.getClass().getSimpleName());
        };
    }

    @Override
    public ContentView viewFromModel(Content model) {
        return switch (model) {
            case Process process -> processMapper.viewFromModel(process);
            case Product product -> productMapper.viewFromModel(product);
            case Bundle bundle -> bundleMapper.viewFromModel(bundle);
            default ->
                    throw new IllegalArgumentException("Unknown content model type: " + model.getClass().getSimpleName());
        };
    }

}
