package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.service.ChatService;

@Service
@Transactional
@AllArgsConstructor
public class ChatServiceImpl extends BaseServiceImpl<ChatEntity, ChatView, ChatResponse, String> implements ChatService {

    private SimpMessagingTemplate messagingTemplate;

    @Override
    public ChatResponse getByIdWithMessages(String id) {
        ChatEntity entity = getEntity(id);
        validator.validateForView(entity);
        Hibernate.initialize(entity.getMessages());
        return mapper.mapToResponse(entity);
    }

    @Override
    public void deleteById(String id) {
        ChatEntity entity = deleteEntity(id);
        messagingTemplate.convertAndSend("/topic/chat/delete/" + id, id);
        entity.getUsers().forEach(user ->
            messagingTemplate.convertAndSend("/topic/user/delete-chat/" + user.getId(), id)
        );
    }
}
