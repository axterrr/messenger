package ua.edu.ukma.hibskyi.messenger.model.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.model.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.model.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.model.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.model.response.MessageResponse;
import ua.edu.ukma.hibskyi.messenger.model.view.MessageView;

import java.util.UUID;

@Component
@AllArgsConstructor
public class MessageMapper extends AbstractMapper<MessageView, MessageResponse, MessageEntity> {

//    private ChatMapper chatMapper;
//    private UserMapper userMapper;

    @Override
    public MessageEntity mapToEntity(MessageView view) {
        return MessageEntity.builder()
            .content(view.getContent())
            .chat(ChatEntity.builder()
                .id(UUID.fromString(view.getChatId()))
                .build())
            .sender(UserEntity.builder()
                .id(UUID.fromString(view.getSenderId()))
                .build())
            .build();
    }

    @Override
    public MessageResponse mapToResponse(MessageEntity entity) {
        return MessageResponse.builder()
            .id(entity.getId().toString())
            .content(entity.getContent())
            .sentAt(entity.getSentAt())
//            .chat(chatMapper.mapToResponse(entity.getChat()))
//            .sender(userMapper.mapToResponse(entity.getSender()))
            .build();
    }
}
