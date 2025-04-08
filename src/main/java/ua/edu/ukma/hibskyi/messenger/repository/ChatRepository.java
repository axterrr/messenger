package ua.edu.ukma.hibskyi.messenger.repository;

import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;

import java.util.List;

public interface ChatRepository extends BaseRepository<ChatEntity, String> {

    boolean existsByIdAndUsersId(String chatId, String userId);

    List<ChatEntity> findByUsersId(String userId);
}
