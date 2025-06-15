package it.unicam.cs.agrotrace.service.storage;

import it.unicam.cs.agrotrace.shared.model.file.CertificationDetails;
import it.unicam.cs.agrotrace.service.storage.factory.FileFactory;
import it.unicam.cs.agrotrace.shared.model.file.Certification;
import org.springframework.stereotype.Service;

@Service
public class CertificationsStorageService extends FileStorageService<Certification, CertificationDetails> {

    public CertificationsStorageService(FileFactory<Certification, CertificationDetails> fileFactory) {
        super("certifications", fileFactory);
    }
}
