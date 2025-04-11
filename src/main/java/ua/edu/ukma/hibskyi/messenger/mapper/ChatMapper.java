package ua.edu.ukma.hibskyi.messenger.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.response.MessageResponse;
import ua.edu.ukma.hibskyi.messenger.dto.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.service.AuthService;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ChatMapper implements BaseMapper<ChatEntity, ChatView, ChatResponse> {

    private AuthService authService;

    @Override
    public ChatEntity mapToEntity(ChatView view) {
        if (view == null) { return null; }
        List<UserEntity> users = new ArrayList<>();
        MapperUtils.ifNotNull(view.getUsersIds(), ids -> users.addAll(ids.stream()
            .map(MapperUtils::userEntityFromId)
            .toList()));

        UserEntity currentUser = MapperUtils.userEntityFromId(authService.getAuthenticatedUserId());
        users.add(currentUser);

        return ChatEntity.builder()
            .name(view.getName())
            .users(users)
            .owner(currentUser)
            .build();
    }

    @Override
    public void merge(ChatView view, ChatEntity entity) {
        if (view == null) { return; }
        MapperUtils.ifNotNull(view.getName(), entity::setName);

        ArrayList<UserEntity> users = new ArrayList<>(entity.getUsers());
        MapperUtils.ifNotNull(view.getUsersIds(), ids -> users.addAll(ids.stream()
            .map(MapperUtils::userEntityFromId)
            .toList()));
        entity.setUsers(users);
    }

    @Override
    public ChatResponse mapToResponse(ChatEntity entity) {
        if (entity == null) { return null; }
        return ChatResponse.builder()
            .id(entity.getId())
            .name(entity.getName())
            .messages(MapperUtils.mapIfInitialized(entity.getMessages(), this::mapMessageToResponse))
            .users(MapperUtils.mapIfInitialized(entity.getUsers(), this::mapUserToResponse))
            .owner(mapUserToResponse(entity.getOwner()))
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

    private MessageResponse mapMessageToResponse(MessageEntity message) {
        if (message == null) { return null; }
        return MessageResponse.builder()
            .id(message.getId())
            .content(message.getContent())
            .sentAt(message.getSentAt())
            .sender(mapUserToResponse(message.getSender()))
            .build();
    }
}
