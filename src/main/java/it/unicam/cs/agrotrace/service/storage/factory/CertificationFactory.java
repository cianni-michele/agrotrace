package it.unicam.cs.agrotrace.service.storage.factory;

import it.unicam.cs.agrotrace.shared.model.file.Certification;
import it.unicam.cs.agrotrace.shared.model.file.CertificationDetails;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.UUID;

@Component
public class CertificationFactory extends FileFactory<Certification, CertificationDetails> {

    @Override
    public Certification createFile(Path filePath, UUID id, CertificationDetails details) {
        return Certification.builder()
                .id(id)
                .path(filePath)
                .type(details.type())
                .build();
    }
}
