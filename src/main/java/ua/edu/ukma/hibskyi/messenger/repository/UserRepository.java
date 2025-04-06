package ua.edu.ukma.hibskyi.messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.hibskyi.messenger.model.entity.UserEntity;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
