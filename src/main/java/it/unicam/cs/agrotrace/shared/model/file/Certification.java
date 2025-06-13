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
public class Certification extends AbstractUploadedFile {

    private final Type type;

    @Builder
    public Certification(UUID id,
                         Path path,
                         Type type) {
        super(id, path);
        this.type = type;
    }

    //TODO: Find more specific types of certifications
    public enum Type {
        BIO
    }
}
