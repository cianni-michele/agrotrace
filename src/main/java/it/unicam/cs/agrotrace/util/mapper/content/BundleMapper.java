package it.unicam.cs.agrotrace.util.mapper.content;

import it.unicam.cs.agrotrace.rest.view.BundleView;
import it.unicam.cs.agrotrace.shared.entity.content.BundleEntity;
import it.unicam.cs.agrotrace.shared.model.content.Bundle;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import it.unicam.cs.agrotrace.util.mapper.user.author.AuthorMapper;

final class BundleMapper extends AbstractContentMapper<BundleView, Bundle, BundleEntity> {

    BundleMapper(AuthorMapper authorMapper) {
        super(authorMapper);
    }

    @Override
    public BundleEntity entityFromModel(Bundle model) {
        BundleEntity entity = new BundleEntity();
        entity.setId(model.getId());
        entity.setTitle(model.getTitle());
        entity.setDescription(model.getDescription());
        entity.setValidationStatus(model.getValidationStatus().name());
        entity.setAuthor(authorMapper.entityFromModel(model.getAuthor()));
        return entity;
    }

    @Override
    public Bundle modelFromEntity(BundleEntity entity) {
        return Bundle.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .validationStatus(ValidationStatus.valueOf(entity.getValidationStatus()))
                .author(authorMapper.modelFromEntity(entity.getAuthor()))
                .build();
    }

    @Override
    public BundleView viewFromModel(Bundle model) {
        BundleView view = new BundleView();
        view.setId(model.getId().toString());
        view.setTitle(model.getTitle());
        view.setDescription(model.getDescription());
        view.setValidationStatus(model.getValidationStatus().name());
        view.setAuthorId(model.getAuthor().getId());
        return view;
    }
}
