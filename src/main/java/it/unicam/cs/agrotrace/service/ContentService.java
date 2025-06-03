package it.unicam.cs.agrotrace.service;

import it.unicam.cs.agrotrace.exception.ContentNotFoundException;
import it.unicam.cs.agrotrace.shared.model.content.Content;
import it.unicam.cs.agrotrace.shared.model.content.ValidationStatus;

import java.util.List;
import java.util.UUID;

public interface ContentService {

    /**
     * Retrieves the content with the specified ID.
     *
     * @param id the unique identifier of the content
     * @return an Optional containing the content if found, or empty if not found
     * @throws IllegalArgumentException if the ID is null
     * @throws ContentNotFoundException if no content with the specified ID exists
     */
    Content findById(UUID id);

    /**
     * Retrieves all contents, optionally filtered by validation status.
     *
     * @param status the validation status to filter by, or null to retrieve all contents
     * @return a list of contents matching the specified validation status
     */
    List<Content> findAll(ValidationStatus status);

    /**
     * Saves the given content to the repository.
     *
     * @param content the content to save
     * @return the saved content
     * @throws IllegalArgumentException if the content is null
     */
    Content save(Content content);
}
