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
import ua.edu.ukma.hibskyi.messenger.service.AuthService;

@Component
@AllArgsConstructor
public class MessageMapper extends BaseMapperImpl<MessageEntity, MessageView, MessageResponse> {

    private AuthService authService;

    @Override
    public MessageEntity mapToEntity(MessageView view) {
        if (view == null) {
            return null;
        }

        return MessageEntity.builder()
            .content(view.getContent())
            .chat(ChatEntity.builder()
                .id(view.getChatId())
                .build())
            .sender(UserEntity.builder()
                .id(authService.getAuthenticatedUserId())
                .build())
            .build();
    }

    @Override
    public MessageResponse mapToResponse(MessageEntity entity) {
        if (entity == null) {
            return null;
        }

        return MessageResponse.builder()
            .id(entity.getId())
            .content(entity.getContent())
            .sentAt(entity.getSentAt())
            .chat(entity.getChat() == null ? null : ChatResponse.builder()
                .id(entity.getChat().getId())
                .name(entity.getChat().getName())
                .owner(mapUserToResponse(entity.getChat().getOwner()))
                .build())
            .sender(mapUserToResponse(entity.getSender()))
            .build();
    }

    private UserResponse mapUserToResponse(UserEntity user) {
        if (user == null) {
            return null;
        }

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
