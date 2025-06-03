package it.unicam.cs.agrotrace.repository;

import it.unicam.cs.agrotrace.shared.entity.user.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository<U extends UserEntity> extends CrudRepository<U, Long> {
}
