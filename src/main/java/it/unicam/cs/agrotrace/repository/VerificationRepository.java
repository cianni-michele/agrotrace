package it.unicam.cs.agrotrace.repository;

import it.unicam.cs.agrotrace.shared.entity.verification.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, Long> {

    /**
     * Finds all verifications associated with a specific content.
     *
     * @param contentId the ID of the content for which verifications are sought
     * @return a list of VerificationEntity objects associated with the specified content ID
     */
    @Query("SELECT v FROM VerificationEntity v WHERE v.content.id = :contentId")
    List<VerificationEntity> findAllByContentId(UUID contentId);

    /**
     * Finds all verifications made by a specific curator.
     *
     * @param curatorId the ID of the curator whose verifications are sought
     * @return a list of VerificationEntity objects made by the specified curator
     */
    @Query("SELECT v FROM VerificationEntity v WHERE v.curator.id = :curatorId")
    List<VerificationEntity> findAllByCuratorId(Long curatorId);
}
