package ua.edu.ukma.hibskyi.messenger.service.implementation;

import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.service.ChatService;

@Service
public class ChatServiceImpl extends BaseServiceImpl<ChatEntity, ChatView, ChatResponse, String> implements ChatService {
}
