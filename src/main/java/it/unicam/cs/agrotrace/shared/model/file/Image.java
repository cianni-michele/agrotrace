package it.unicam.cs.agrotrace.shared.model.file;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.nio.file.Path;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Image extends AbstractUploadedFile {

    private final String description;

    @Builder
    public Image(UUID id,
                 Path path,
                 String description) {
        super(id, path);
        this.description = description;
    }

}
