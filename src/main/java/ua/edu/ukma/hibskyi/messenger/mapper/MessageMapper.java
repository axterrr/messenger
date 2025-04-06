package ua.edu.ukma.hibskyi.messenger.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.model.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.model.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.model.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.model.response.MessageResponse;
import ua.edu.ukma.hibskyi.messenger.model.view.MessageView;

@Component
@AllArgsConstructor
public class MessageMapper extends BaseMapperImpl<MessageEntity, MessageView, MessageResponse> {

//    private ChatMapper chatMapper;
//    private UserMapper userMapper;

    @Override
    public MessageEntity mapToEntity(MessageView view) {
        return MessageEntity.builder()
            .content(view.getContent())
            .chat(ChatEntity.builder()
                .id(view.getChatId())
                .build())
            .sender(UserEntity.builder()
                .id(view.getSenderId())
                .build())
            .build();
    }

    @Override
    public MessageResponse mapToResponse(MessageEntity entity) {
        return MessageResponse.builder()
            .id(entity.getId())
            .content(entity.getContent())
            .sentAt(entity.getSentAt())
//            .chat(chatMapper.mapToResponse(entity.getChat()))
//            .sender(userMapper.mapToResponse(entity.getSender()))
            .build();
    }
}
