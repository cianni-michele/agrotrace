package it.unicam.cs.agrotrace.shared.model.file;

import java.nio.file.Path;

public interface UploadedFile {

    /**
     * Returns the name of the file.
     *
     * @return the name of the file
     */
    default String getName() {
        return getPath().getFileName().toString();
    }


    /**
     * Returns the path to the file.
     *
     * @return the path to the file
     */
    Path getPath();

}
