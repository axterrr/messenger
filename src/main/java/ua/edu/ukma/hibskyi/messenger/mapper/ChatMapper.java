package ua.edu.ukma.hibskyi.messenger.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;

import java.util.ArrayList;

@Component
@AllArgsConstructor
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
//            .messages(entity.getMessages().stream()
//                .map(messageMapper::mapToResponse)
//                .toList())
//            .users(entity.getUsers().stream()
//                .map(userMapper::mapToResponse)
//                .toList())
            .build();
    }
}
