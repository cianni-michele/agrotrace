package it.unicam.cs.agrotrace.repository;

import it.unicam.cs.agrotrace.shared.entity.content.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContentRepository extends JpaRepository<ContentEntity, UUID> {
    List<ContentEntity> findAllByValidationStatus(String status);
}
