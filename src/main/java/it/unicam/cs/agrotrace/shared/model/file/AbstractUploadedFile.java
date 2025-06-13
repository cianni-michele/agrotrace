package it.unicam.cs.agrotrace.shared.model.file;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.nio.file.Path;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public abstract class AbstractUploadedFile implements UploadedFile {

    protected final UUID id;

    protected final Path path;

    protected AbstractUploadedFile(UUID id, Path path) {
        this.id = id;
        this.path = path;
    }

}
