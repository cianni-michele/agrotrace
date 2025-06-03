package it.unicam.cs.agrotrace.util.mapper.content;

import it.unicam.cs.agrotrace.rest.view.ProcessView;
import it.unicam.cs.agrotrace.shared.entity.content.ProcessEntity;
import it.unicam.cs.agrotrace.shared.model.content.Process;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import it.unicam.cs.agrotrace.util.mapper.user.author.AuthorMapper;

final class ProcessMapper extends AbstractContentMapper<ProcessView, Process, ProcessEntity> {

    ProcessMapper(AuthorMapper authorMapper) {
        super(authorMapper);
    }

    @Override
    public ProcessEntity entityFromModel(Process model) {
        ProcessEntity entity = new ProcessEntity();
        entity.setId(model.getId());
        entity.setTitle(model.getTitle());
        entity.setDescription(model.getDescription());
        entity.setValidationStatus(model.getValidationStatus().name());
        entity.setAuthor(authorMapper.entityFromModel(model.getAuthor()));
        return entity;
    }

    @Override
    public Process modelFromEntity(ProcessEntity entity) {
        return Process.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .validationStatus(ValidationStatus.valueOf(entity.getValidationStatus()))
                .author(authorMapper.modelFromEntity(entity.getAuthor()))
                .build();
    }

    @Override
    public ProcessView viewFromModel(Process model) {
        ProcessView view = new ProcessView();
        view.setId(model.getId().toString());
        view.setTitle(model.getTitle());
        view.setDescription(model.getDescription());
        view.setValidationStatus(model.getValidationStatus().name());
        view.setAuthorId(model.getAuthor().getId());
        return view;
    }
}
