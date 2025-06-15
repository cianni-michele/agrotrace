package it.unicam.cs.agrotrace.service.storage;

import it.unicam.cs.agrotrace.service.storage.factory.FileFactory;
import it.unicam.cs.agrotrace.shared.model.file.ImageDetails;
import it.unicam.cs.agrotrace.shared.model.file.Image;
import org.springframework.stereotype.Service;

@Service
public class ImageStorageService extends FileStorageService<Image, ImageDetails> {

    public ImageStorageService(FileFactory<Image, ImageDetails> fileFactory) {
        super("images", fileFactory);
    }
}
