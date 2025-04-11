package ua.edu.ukma.hibskyi.messenger.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.service.AuthService;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ChatMapper extends BaseMapperImpl<ChatEntity, ChatView, ChatResponse> {

//    private MessageMapper messageMapper;
    private UserMapper userMapper;
    private AuthService authService;

    @Override
    public ChatEntity mapToEntity(ChatView view) {
        if (view == null) {
            return null;
        }

        UserEntity currentUser = UserEntity.builder().id(authService.getAuthenticatedUser().getId()).build();
        List<UserEntity> userList = new ArrayList<>();
        userList.add(currentUser);
        if (view.getUsersIds() != null) {
            userList.addAll(view.getUsersIds().stream()
                .map(id -> UserEntity.builder().id(id).build())
                .toList());
        }

        return ChatEntity.builder()
            .name(view.getName())
            .users(userList)
            .owner(currentUser)
            .build();
    }

    @Override
    public ChatResponse mapToResponse(ChatEntity entity) {
        if (entity == null) {
            return null;
        }

        return ChatResponse.builder()
            .id(entity.getId())
            .name(entity.getName())
//            .messages(mapIfInitialized(entity.getMessages(), messageMapper::mapToResponse))
//            .users(mapIfInitialized(entity.getUsers(), userMapper::mapToResponse))
            .owner(userMapper.mapToResponse(entity.getOwner()))
            .build();
    }
}
