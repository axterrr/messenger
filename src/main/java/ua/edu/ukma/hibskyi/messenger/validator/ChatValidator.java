package ua.edu.ukma.hibskyi.messenger.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.exception.ValidationException;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;

@Service
@AllArgsConstructor
public class ChatValidator extends BaseValidatorImpl<ChatEntity, String> {

    private final UserRepository userRepository;

    @Override
    public void validateForCreate(ChatEntity entity) {
        super.validateForCreate(entity);

        if (entity.getMessages() != null && !entity.getMessages().isEmpty()) {
            throw new ValidationException("Cannot create chat with messages");
        }
        if (entity.getUsers().stream().anyMatch(u -> !userRepository.existsById(u.getId()))) {
            throw new ValidationException("Cannot create chat with non existing users");
        }
    }
}
