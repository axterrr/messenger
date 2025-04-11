package ua.edu.ukma.hibskyi.messenger.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.response.MessageResponse;
import ua.edu.ukma.hibskyi.messenger.dto.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.service.AuthService;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ChatMapper extends BaseMapperImpl<ChatEntity, ChatView, ChatResponse> {

    private AuthService authService;

    @Override
    public ChatEntity mapToEntity(ChatView view) {
        if (view == null) {
            return null;
        }

        UserEntity currentUser = UserEntity.builder().id(authService.getAuthenticatedUserId()).build();
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
            .messages(mapIfInitialized(entity.getMessages(), message -> MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .sender(mapUserToResponse(message.getSender()))
                .build()))
            .users(mapIfInitialized(entity.getUsers(), this::mapUserToResponse))
            .owner(mapUserToResponse(entity.getOwner()))
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
