package it.unicam.cs.agrotrace.service.storage.factory;

import it.unicam.cs.agrotrace.shared.model.file.Image;
import it.unicam.cs.agrotrace.shared.model.file.ImageDetails;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.UUID;

@Component
public class ImageFactory extends FileFactory<Image, ImageDetails> {

    @Override
    public Image createFile(Path path, UUID id, ImageDetails details) {
        return Image.builder()
                .id(id)
                .path(path)
                .description(details.imageDescription())
                .build();
    }
}
