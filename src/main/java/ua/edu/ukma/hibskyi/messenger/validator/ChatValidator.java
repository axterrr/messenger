package ua.edu.ukma.hibskyi.messenger.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.exception.ConflictException;
import ua.edu.ukma.hibskyi.messenger.exception.ForbiddenException;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;

@Component
@AllArgsConstructor
public class ChatValidator extends BaseValidatorImpl<ChatEntity, ChatView, String> {

    private final UserRepository userRepository;

    @Override
    public void validateForCreate(ChatView view) {
        super.validateForCreate(view);

        if (view.getUsersIds().stream().anyMatch(id -> !userRepository.existsById(id))) {
            throw new ConflictException("Cannot create chat with non existing users");
        }
    }

    @Override
    public void validateForUpdate(ChatView view, ChatEntity entity) {
        super.validateForUpdate(view, entity);

        if (entity.getUsers().stream().anyMatch(u -> !userRepository.existsById(u.getId()))) {
            throw new ConflictException("Cannot add non existing users");
        }
    }

    public void validateForLeave(ChatEntity chat, UserEntity user) {
        if (!chat.getUsers().contains(user)) {
            throw new ConflictException("User already isn't in the chat");
        }
        if (chat.getOwner() == user) {
            throw new ConflictException("Owner cannot be deleted from his chat");
        }
    }

    public void validateForDeleteUserFromChat(ChatEntity chat, UserEntity user) {
        validatePermissionForUpdate(chat);
        validateForLeave(chat, user);
    }

    @Override
    protected void validatePermissionForView(ChatEntity entity) {
        String currentUserId = authService.getAuthenticatedUserId();
        if (entity.getUsers().stream().noneMatch(u -> u.getId().equals(currentUserId))) {
            throw new ForbiddenException();
        }
    }

    @Override
    protected void validatePermissionForCreate(ChatView view) {

    }

    @Override
    protected void validatePermissionForUpdate(ChatEntity entity) {
        if (!entity.getOwner().getId().equals(authService.getAuthenticatedUserId())) {
            throw new ForbiddenException();
        }
    }

    @Override
    protected void validatePermissionForDelete(ChatEntity entity) {
        if (!entity.getOwner().getId().equals(authService.getAuthenticatedUserId())) {
            throw new ForbiddenException();
        }
    }
}
