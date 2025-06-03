package it.unicam.cs.agrotrace.service;

import it.unicam.cs.agrotrace.exception.ContentNotFoundException;
import it.unicam.cs.agrotrace.shared.entity.content.ContentEntity;
import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;
import it.unicam.cs.agrotrace.repository.ContentRepository;
import it.unicam.cs.agrotrace.util.mapper.content.ContentMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GenericContentService implements ContentService {

    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

    public GenericContentService(ContentRepository contentRepository, ContentMapper contentMapper) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
    }

    @Override
    public Content findById(UUID id) {
        return Optional.ofNullable(id)
                .map(contentRepository::findById)
                .orElseThrow(() -> new IllegalArgumentException("Content ID cannot be null"))
                .map(contentMapper::modelFromEntity)
                .orElseThrow(() -> new ContentNotFoundException(id));
    }

    @Override
    public List<Content> findAll(ValidationStatus status) {
        return Optional.ofNullable(status)
                .map(ValidationStatus::name)
                .map(contentRepository::findAllByValidationStatus)
                .orElseGet(contentRepository::findAll).stream()
                .map(contentMapper::modelFromEntity)
                .toList();
    }

    @Override
    public Content save(Content content) {
        return Optional.ofNullable(content)
                .map(contentMapper::entityFromModel)
                .map(contentRepository::save)
                .map(contentMapper::modelFromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Content cannot be null"));
    }

}
