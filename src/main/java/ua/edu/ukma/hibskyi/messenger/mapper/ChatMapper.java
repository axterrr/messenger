package ua.edu.ukma.hibskyi.messenger.mapper;

import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;

import java.util.ArrayList;

@Component
public class ChatMapper extends BaseMapperImpl<ChatEntity, ChatView, ChatResponse> {

//    private MessageMapper messageMapper;
//    private UserMapper userMapper;

    @Override
    public ChatEntity mapToEntity(ChatView view) {
        return ChatEntity.builder()
            .name(view.getName())
            .users(view.getUsersIds() == null ? new ArrayList<>() : view.getUsersIds().stream()
                .map(id -> UserEntity.builder()
                    .id(id).build())
                    .toList())
            .build();
    }

    @Override
    public ChatResponse mapToResponse(ChatEntity entity) {
        return ChatResponse.builder()
            .id(entity.getId())
            .name(entity.getName())
//            .messages(mapIfInitialized(entity.getMessages(), messageMapper::mapToResponse))
//            .users(mapIfInitialized(entity.getUsers(), userMapper::mapToResponse))
            .build();
    }
}
