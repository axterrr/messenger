package ua.edu.ukma.hibskyi.messenger.model.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.model.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.model.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.model.view.UserView;

@Component
@AllArgsConstructor
public class UserMapper extends AbstractMapper<UserView, UserResponse, UserEntity> {

//    private ChatMapper chatMapper;

    @Override
    public UserEntity mapToEntity(UserView view) {
        return UserEntity.builder()
            .phone(view.getPhone())
            .email(view.getEmail())
            .name(view.getName())
            .description(view.getDescription())
            .build();
    }

    @Override
    public UserResponse mapToResponse(UserEntity entity) {
        return UserResponse.builder()
            .id(entity.getId().toString())
            .phone(entity.getPhone())
            .email(entity.getEmail())
            .name(entity.getName())
            .description(entity.getDescription())
//            .chats(entity.getChats().stream()
//                .map(chatMapper::mapToResponse)
//                .toList())
            .build();
    }
}
