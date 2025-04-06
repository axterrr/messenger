package ua.edu.ukma.hibskyi.messenger.model.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.model.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.model.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.model.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.model.view.ChatView;

import java.util.UUID;

@Component
@AllArgsConstructor
public class ChatMapper extends AbstractMapper<ChatView, ChatResponse, ChatEntity> {

//    private MessageMapper messageMapper;
//    private UserMapper userMapper;

    @Override
    public ChatEntity mapToEntity(ChatView view) {
        return ChatEntity.builder()
            .name(view.getName())
            .users(view.getUsersIds()
                .stream()
                .map(id -> UserEntity.builder()
                    .id(UUID.fromString(id)).build())
                    .toList())
            .build();
    }

    @Override
    public ChatResponse mapToResponse(ChatEntity entity) {
        return ChatResponse.builder()
            .id(entity.getId().toString())
            .name(entity.getName())
//            .messages(entity.getMessages().stream()
//                .map(messageMapper::mapToResponse)
//                .toList())
//            .users(entity.getUsers().stream()
//                .map(userMapper::mapToResponse)
//                .toList())
            .build();
    }
}
