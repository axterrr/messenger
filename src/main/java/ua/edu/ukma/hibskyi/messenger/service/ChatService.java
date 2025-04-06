package ua.edu.ukma.hibskyi.messenger.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.model.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.repository.ChatRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ChatService implements GenericService<ChatEntity, UUID> {

    private ChatRepository chatRepository;

    @Override
    public ChatEntity getById(UUID id) {
        return chatRepository.findById(id).orElseThrow();
    }

    @Override
    public UUID create(ChatEntity entity) {
        return chatRepository.save(entity).getId();
    }

    @Override
    public void update(UUID id, ChatEntity entity) {
        entity.setId(id);
        chatRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        chatRepository.deleteById(id);
    }
}
