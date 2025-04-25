package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.exception.NotFoundException;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;
import ua.edu.ukma.hibskyi.messenger.service.AuthService;
import ua.edu.ukma.hibskyi.messenger.service.ChatService;
import ua.edu.ukma.hibskyi.messenger.validator.ChatValidator;

@Service
@Transactional
@AllArgsConstructor
public class ChatServiceImpl extends BaseServiceImpl<ChatEntity, ChatView, ChatResponse, String> implements ChatService {

    private SimpMessagingTemplate messagingTemplate;
    private UserRepository userRepository;
    private ChatValidator chatValidator;
    private AuthService authService;

    @Override
    public ChatResponse getByIdWithMessages(String id) {
        ChatEntity entity = getEntity(id);
        validator.validateForView(entity);
        Hibernate.initialize(entity.getMessages());
        return mapper.mapToResponse(entity);
    }

    @Override
    public void leaveChat(String chatId) {
        ChatEntity chat = getEntity(chatId);
        String userId = authService.getAuthenticatedUserId();
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found"));
        chatValidator.validateForLeave(chat, user);
        chat.getUsers().remove(user);
    }

    @Override
    public void deleteUserFromChat(String chatId, String userId) {
        ChatEntity chat = getEntity(chatId);
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found"));
        chatValidator.validateForDeleteUserFromChat(chat, user);
        chat.getUsers().remove(user);
        messagingTemplate.convertAndSend("/topic/chat/" + chatId + "/delete-user/" + userId, chatId);
        messagingTemplate.convertAndSend("/topic/user/delete-chat/" + user.getId(), chat.getId());
    }

    @Override
    public void deleteById(String id) {
        ChatEntity entity = deleteEntity(id);
        entity.getUsers().forEach(user -> {
            messagingTemplate.convertAndSend("/topic/chat/" + id + "/delete-user/" + user.getId(), id);
            messagingTemplate.convertAndSend("/topic/user/delete-chat/" + user.getId(), id);
        });
    }
}
