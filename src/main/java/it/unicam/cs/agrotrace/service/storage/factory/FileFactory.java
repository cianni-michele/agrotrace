package it.unicam.cs.agrotrace.service.storage.factory;

import it.unicam.cs.agrotrace.shared.model.file.FileDetails;
import it.unicam.cs.agrotrace.shared.model.file.UploadedFile;

import java.nio.file.Path;
import java.util.UUID;

/**
 * Abstract factory class for creating file instances.
 *
 * @param <F> the certificationType of file to create
 * @param <D> the certificationType of file details associated with the file
 */
public abstract class FileFactory<F extends UploadedFile, D extends FileDetails> {

    public abstract F createFile(Path filePath, UUID id, D details);
}
