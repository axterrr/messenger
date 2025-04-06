package ua.edu.ukma.hibskyi.messenger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.hibskyi.messenger.model.entity.ChatEntity;

import java.util.UUID;

public interface ChatRepository extends JpaRepository<ChatEntity, UUID> {
}
