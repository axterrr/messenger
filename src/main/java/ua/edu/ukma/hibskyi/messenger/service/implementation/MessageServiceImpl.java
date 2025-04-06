package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.model.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.model.mapper.MessageMapper;
import ua.edu.ukma.hibskyi.messenger.model.response.MessageResponse;
import ua.edu.ukma.hibskyi.messenger.model.view.MessageView;
import ua.edu.ukma.hibskyi.messenger.repository.MessageRepository;
import ua.edu.ukma.hibskyi.messenger.service.MessageService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;
    private MessageMapper messageMapper;

    @Override
    public List<MessageResponse> getAll() {
        List<MessageEntity> result = messageRepository.findAll();
        return messageMapper.mapToResponse(result);
    }

    @Override
    public MessageResponse getById(String id) {
        MessageEntity result = messageRepository.findById(UUID.fromString(id)).orElseThrow();
        return messageMapper.mapToResponse(result);
    }

    @Override
    public String create(MessageView view) {
        MessageEntity entity = messageMapper.mapToEntity(view);
        UUID id = messageRepository.save(entity).getId();
        return id.toString();
    }

    @Override
    public void update(String id, MessageView view) {
        MessageEntity entity = messageMapper.mapToEntity(view);
        entity.setId(UUID.fromString(id));
        messageRepository.save(entity);
    }

    @Override
    public void deleteById(String id) {
        messageRepository.deleteById(UUID.fromString(id));
    }
}
