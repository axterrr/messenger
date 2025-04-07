package ua.edu.ukma.hibskyi.messenger.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.exception.ValidationException;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserValidator extends BaseValidatorImpl<UserEntity, String> {

    private final UserRepository userRepository;

    @Override
    public void validateForCreate(UserEntity entity) {
        super.validateForCreate(entity);

        if (entity.getChats() != null && !entity.getChats().isEmpty()) {
            throw new ValidationException("Cannot create user with chats");
        }
        if (entity.getMessages() != null && !entity.getMessages().isEmpty()) {
            throw new ValidationException("Cannot create user with messages");
        }
        if (userRepository.existsByPhone(entity.getPhone())) {
            throw new ValidationException("User with such phone already exists");
        }
        if (userRepository.existsByEmail(entity.getEmail())) {
            throw new ValidationException("User with such email already exists");
        }
    }

    @Override
    public void validateForUpdate(UserEntity entity) {
        super.validateForUpdate(entity);

        userRepository.findByPhone(entity.getPhone()).ifPresent(existing -> {
            if (!existing.getId().equals(entity.getId())) {
                throw new ValidationException("User with such phone already exists");
            }
        });
        userRepository.findByEmail(entity.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(entity.getId())) {
                throw new ValidationException("User with such email already exists");
            }
        });
    }
}
