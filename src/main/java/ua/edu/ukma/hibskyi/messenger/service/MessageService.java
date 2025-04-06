package ua.edu.ukma.hibskyi.messenger.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.model.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.repository.MessageRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageService implements GenericService<MessageEntity, UUID> {

    private MessageRepository messageRepository;

    @Override
    public MessageEntity getById(UUID id) {
        return messageRepository.findById(id).orElseThrow();
    }

    @Override
    public UUID create(MessageEntity entity) {
        return messageRepository.save(entity).getId();
    }

    @Override
    public void update(UUID id, MessageEntity entity) {
        entity.setId(id);
        messageRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        messageRepository.deleteById(id);
    }
}
