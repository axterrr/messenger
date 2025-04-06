package ua.edu.ukma.hibskyi.messenger.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.hibskyi.messenger.model.entity.MessageEntity;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
}
