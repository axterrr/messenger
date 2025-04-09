package ua.edu.ukma.hibskyi.messenger.repository;


import ua.edu.ukma.hibskyi.messenger.entity.MessageEntity;

import java.util.List;

public interface MessageRepository extends BaseRepository<MessageEntity, String> {

    List<MessageEntity> findByChatIdOrderBySentAt(String chatId);
}
