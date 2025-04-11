package ua.edu.ukma.hibskyi.messenger.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.dto.response.ChatResponse;
import ua.edu.ukma.hibskyi.messenger.dto.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.UserView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;

@Component
@AllArgsConstructor
public class UserMapper implements BaseMapper<UserEntity, UserView, UserResponse> {

    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity mapToEntity(UserView view) {
        if (view == null) { return null; }
        return UserEntity.builder()
            .phone(view.getPhone())
            .username(view.getUsername())
            .email(view.getEmail())
            .name(view.getName())
            .description(view.getDescription())
            .passwordHash(passwordEncoder.encode(view.getPassword()))
            .build();
    }

    @Override
    public void merge(UserView view, UserEntity entity) {
        if (view == null) { return; }
        MapperUtils.ifNotNull(view.getPhone(), entity::setPhone);
        MapperUtils.ifNotNull(view.getUsername(), entity::setUsername);
        MapperUtils.ifNotNull(view.getEmail(), entity::setEmail);
        MapperUtils.ifNotNull(view.getName(), entity::setName);
        MapperUtils.ifNotNull(view.getDescription(), entity::setDescription);
        MapperUtils.ifNotNull(view.getPassword(), password -> entity.setPasswordHash(passwordEncoder.encode(password)));
    }

    @Override
    public UserResponse mapToResponse(UserEntity entity) {
        if (entity == null) { return null; }
        return UserResponse.builder()
            .id(entity.getId())
            .phone(entity.getPhone())
            .username(entity.getUsername())
            .email(entity.getEmail())
            .name(entity.getName())
            .description(entity.getDescription())
            .chats(MapperUtils.mapIfInitialized(entity.getChats(), this::mapChatToResponse))
            .build();
    }

    private ChatResponse mapChatToResponse(ChatEntity chat) {
        if (chat == null) { return null; }
        return ChatResponse.builder()
            .id(chat.getId())
            .name(chat.getName())
            .build();
    }
}
