package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.model.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.model.mapper.ChatMapper;
import ua.edu.ukma.hibskyi.messenger.model.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.model.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.repository.ChatRepository;
import ua.edu.ukma.hibskyi.messenger.service.ChatService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private ChatRepository chatRepository;
    private ChatMapper chatMapper;

    @Override
    public List<ChatResponse> getAll() {
        List<ChatEntity> result = chatRepository.findAll();
        return chatMapper.mapToResponse(result);
    }

    @Override
    public ChatResponse getById(String id) {
        ChatEntity result = chatRepository.findById(UUID.fromString(id)).orElseThrow();
        return chatMapper.mapToResponse(result);
    }

    @Override
    public String create(ChatView view) {
        ChatEntity entity = chatMapper.mapToEntity(view);
        UUID id = chatRepository.save(entity).getId();
        return id.toString();
    }

    @Override
    public void update(String id, ChatView view) {
        ChatEntity entity = chatMapper.mapToEntity(view);
        entity.setId(UUID.fromString(id));
        chatRepository.save(entity);
    }

    @Override
    public void deleteById(String id) {
        chatRepository.deleteById(UUID.fromString(id));
    }
}
