package ua.edu.ukma.hibskyi.messenger.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.dto.response.MessageResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.MessageView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.service.AuthService;

@Component
@AllArgsConstructor
public class MessageMapper extends BaseMapperImpl<MessageEntity, MessageView, MessageResponse> {

    private ChatMapper chatMapper;
    private UserMapper userMapper;
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
                .id(authService.getAuthenticatedUser().getId())
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
            .chat(chatMapper.mapToResponse(entity.getChat()))
            .sender(userMapper.mapToResponse(entity.getSender()))
            .build();
    }
}
