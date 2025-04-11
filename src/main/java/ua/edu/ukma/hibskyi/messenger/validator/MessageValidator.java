package ua.edu.ukma.hibskyi.messenger.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.dto.view.MessageView;
import ua.edu.ukma.hibskyi.messenger.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.exception.ConflictException;
import ua.edu.ukma.hibskyi.messenger.exception.ForbiddenException;
import ua.edu.ukma.hibskyi.messenger.repository.ChatRepository;

@Component
@AllArgsConstructor
public class MessageValidator extends BaseValidatorImpl<MessageEntity, MessageView, String> {

    private ChatRepository chatRepository;

    @Override
    public void validateForCreate(MessageView view) {
        super.validateForCreate(view);

        if (!chatRepository.existsById(view.getChatId())) {
            throw new ConflictException("Cannot create message in non existing chat");
        }
    }

    @Override
    protected void validatePermissionForView(MessageEntity entity) {
        if (!chatRepository.existsByIdAndUsersId(entity.getChat().getId(), authService.getAuthenticatedUser().getId())) {
            throw new ForbiddenException();
        }
    }

    @Override
    protected void validatePermissionForCreate(MessageView view) {
        if (!chatRepository.existsByIdAndUsersId(view.getChatId(), authService.getAuthenticatedUser().getId())) {
            throw new ForbiddenException();
        }
    }

    @Override
    protected void validatePermissionForUpdate(MessageEntity entity) {
        if (!entity.getSender().getId().equals(authService.getAuthenticatedUser().getId())) {
            throw new ForbiddenException();
        }
    }

    @Override
    protected void validatePermissionForDelete(MessageEntity entity) {
        if (!entity.getSender().getId().equals(authService.getAuthenticatedUser().getId())) {
            throw new ForbiddenException();
        }
    }
}
