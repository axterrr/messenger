package ua.edu.ukma.hibskyi.messenger.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.exception.ValidationException;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;

@Component
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
        if (entity.getEmail() != null && userRepository.existsByEmail(entity.getEmail())) {
            throw new ValidationException("User with such email already exists");
        }
        if (userRepository.existsByUsername(entity.getUsername())) {
            throw new ValidationException("User with such username already exists");
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
        if (entity.getEmail() != null) {
            userRepository.findByEmail(entity.getEmail()).ifPresent(existing -> {
                if (!existing.getId().equals(entity.getId())) {
                    throw new ValidationException("User with such email already exists");
                }
            });
        }
        userRepository.findByUsername(entity.getUsername()).ifPresent(existing -> {
            if (!existing.getId().equals(entity.getId())) {
                throw new ValidationException("User with such username already exists");
            }
        });
    }
}
