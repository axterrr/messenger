package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.service.ChatService;

@Service
@AllArgsConstructor
public class ChatServiceImpl extends BaseServiceImpl<ChatEntity, ChatView, ChatResponse, String> implements ChatService {

    @Override
    public ChatResponse getByIdWithMessages(String id) {
        ChatEntity entity = getEntityById(id);
        validator.validateForView(entity);
        Hibernate.initialize(entity.getMessages());
        return mapper.mapToResponse(entity);
    }
}
