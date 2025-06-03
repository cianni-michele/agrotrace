package it.unicam.cs.agrotrace.service;

import it.unicam.cs.agrotrace.shared.model.verification.Verification;

import java.util.List;
import java.util.UUID;

public interface VerificationService {

    /**
     * Creates new verification for the given content by the specified curator.
     *
     * @param curatorId the curator who is verifying the content
     * @param contentId the content to be verified
     * @param comments  additional comments or notes regarding the verification
     * @return a new Verification object representing the verification
     * @throws IllegalArgumentException if curator or content is null
     */
    Verification createVerification(Long curatorId, UUID contentId, String comments);

    /**
     * Finds verification by its ID.
     *
     * @param id the ID of the verification to find
     * @return an Optional containing the found Verification, or empty if not found
     * @throws IllegalArgumentException if the ID is null
     */
    Verification findById(Long id);

    /**
     * Finds all verifications made by a specific curator.
     *
     * @param curatorId the ID of the curator
     * @return a list of verifications made by the specified curator
     * @throws IllegalArgumentException if the curatorId is null
     */
    List<Verification> findAllByCuratorId(Long curatorId);

    /**
     * Finds all verifications associated with a specific content ID.
     *
     * @param contentId the ID of the content
     * @return a list of verifications associated with the specified content
     * @throws IllegalArgumentException if the contentId is null
     */
    List<Verification> findAllByContentId(UUID contentId);

}
