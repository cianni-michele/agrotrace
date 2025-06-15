package it.unicam.cs.agrotrace.service.storage;

import it.unicam.cs.agrotrace.shared.model.file.FileDetails;
import it.unicam.cs.agrotrace.service.storage.factory.FileFactory;
import it.unicam.cs.agrotrace.shared.model.file.UploadedFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public abstract class FileStorageService<F extends UploadedFile, D extends FileDetails> {

    protected final Path fileStorageLocation;

    protected final FileFactory<F, D> fileFactory;

    protected FileStorageService(String uploadDir, FileFactory<F, D> fileFactory) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory: " + uploadDir, e);
        }
        this.fileFactory = fileFactory;
    }

    protected Path getFileStorageLocation() {
        return fileStorageLocation;
    }

    /**
     * Stores the given file and returns a new instance of the file certificationType with the stored file's path.
     *
     * @param file the file to store
     * @param details additional details about the file, such as metadata
     * @return a new instance of the file certificationType with the stored file's path
     */
    public F storeFile(MultipartFile file, D details) {
        UUID id = UUID.randomUUID();
        Path filePath = storeFile(file, id);
        return fileFactory.createFile(filePath, id, details);
    }

    // This method is marked with @SuppressWarnings("JvmTaintAnalysis")
    // to avoid warnings about potential security issues,
    // as it is assumed that the file input is trusted.
    @SuppressWarnings("JvmTaintAnalysis")
    private Path storeFile(MultipartFile file, UUID id) {
        String fileName = id + "_" + file.getOriginalFilename();
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return targetLocation;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName, ex);
        }
    }

}
