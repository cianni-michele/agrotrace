package it.unicam.cs.agrotrace.util.mapper.file;

import it.unicam.cs.agrotrace.rest.request.ImageMetadataRequest;
import it.unicam.cs.agrotrace.service.storage.factory.ImageDetails;
import it.unicam.cs.agrotrace.shared.entity.file.ImageEntity;
import it.unicam.cs.agrotrace.shared.model.file.Image;
import it.unicam.cs.agrotrace.util.mapper.EntityMapper;
import it.unicam.cs.agrotrace.util.mapper.RequestMapper;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class ImageMapper implements
        EntityMapper<Image, ImageEntity>,
        RequestMapper<ImageMetadataRequest, ImageDetails>
{

    @Override
    public ImageEntity entityFromModel(Image model) {
        ImageEntity entity = new ImageEntity();
        entity.setId(model.getId());
        entity.setPath(model.getPath().toString());
        entity.setDescription(model.getDescription());
        return entity;
    }

    @Override
    public Image modelFromEntity(ImageEntity entity) {
        return Image.builder()
                .id(entity.getId())
                .path(Path.of(entity.getPath()))
                .description(entity.getDescription())
                .build();
    }

    @Override
    public ImageDetails modelFromRequest(ImageMetadataRequest request) {
        return new ImageDetails(
                request.getDescription()
        );
    }
}
