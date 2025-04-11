package ua.edu.ukma.hibskyi.messenger.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.response.MessageResponse;
import ua.edu.ukma.hibskyi.messenger.dto.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.MessageView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.exception.NotFoundException;
import ua.edu.ukma.hibskyi.messenger.repository.ChatRepository;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;
import ua.edu.ukma.hibskyi.messenger.service.AuthService;

@Component
@AllArgsConstructor
public class MessageMapper implements BaseMapper<MessageEntity, MessageView, MessageResponse> {

    private AuthService authService;
    private UserRepository userRepository;
    private ChatRepository chatRepository;

    @Override
    public MessageEntity mapToEntity(MessageView view) {
        if (view == null) { return null; }
        return MessageEntity.builder()
            .content(view.getContent())
            .chat(chatRepository.findById(view.getChatId())
                    .orElseThrow(() -> new NotFoundException("Unknown chat")))
            .sender(userRepository.findById(authService.getAuthenticatedUserId())
                    .orElseThrow(() -> new NotFoundException("Unknown user")))
            .build();
    }

    @Override
    public void merge(MessageView view, MessageEntity entity) {
        if (view == null) { return; }
        MapperUtils.ifNotNull(view.getContent(), entity::setContent);
    }

    @Override
    public MessageResponse mapToResponse(MessageEntity entity) {
        if (entity == null) { return null; }
        return MessageResponse.builder()
            .id(entity.getId())
            .content(entity.getContent())
            .sentAt(entity.getSentAt())
            .chat(mapChatToResponse(entity.getChat()))
            .sender(mapUserToResponse(entity.getSender()))
            .build();
    }

    private ChatResponse mapChatToResponse(ChatEntity chat) {
        if (chat == null) { return null; }
        return ChatResponse.builder()
            .id(chat.getId())
            .name(chat.getName())
            .build();
    }

    private UserResponse mapUserToResponse(UserEntity user) {
        if (user == null) { return null; }
        return UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .phone(user.getPhone())
            .email(user.getEmail())
            .name(user.getName())
            .description(user.getDescription())
            .build();
    }
}
