package ua.edu.ukma.hibskyi.messenger.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.dto.view.ChatView;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
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

    @Override
    protected void validatePermissionForView(ChatEntity entity) {
        String currentUserId = authService.getAuthenticatedUser().getId();
        if (entity.getUsers().stream().noneMatch(u -> u.getId().equals(currentUserId))) {
            throw new ForbiddenException();
        }
    }

    @Override
    protected void validatePermissionForCreate(ChatView view) {

    }

    @Override
    protected void validatePermissionForUpdate(ChatEntity entity) {
        if (!entity.getOwner().getId().equals(authService.getAuthenticatedUser().getId())) {
            throw new ForbiddenException();
        }
    }

    @Override
    protected void validatePermissionForDelete(ChatEntity entity) {
        if (!entity.getOwner().getId().equals(authService.getAuthenticatedUser().getId())) {
            throw new ForbiddenException();
        }
    }
}
