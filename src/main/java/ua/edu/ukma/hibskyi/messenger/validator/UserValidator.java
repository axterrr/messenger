package ua.edu.ukma.hibskyi.messenger.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.dto.view.UserView;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.exception.ConflictException;
import ua.edu.ukma.hibskyi.messenger.exception.ValidationException;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;

@Component
@AllArgsConstructor
public class UserValidator extends BaseValidatorImpl<UserEntity, UserView, String> {

    private final UserRepository userRepository;

    @Override
    public void validateForCreate(UserView view) {
        super.validateForCreate(view);

        validatePassword(view.getPassword(), view.getConfirmPassword());

        if (userRepository.existsByPhone(view.getPhone())) {
            throw new ConflictException("User with such phone already exists");
        }
        if (view.getEmail() != null && userRepository.existsByEmail(view.getEmail())) {
            throw new ConflictException("User with such email already exists");
        }
        if (userRepository.existsByUsername(view.getUsername())) {
            throw new ConflictException("User with such username already exists");
        }
    }

    @Override
    public void validateForUpdate(UserView view, UserEntity entity) {
        super.validateForUpdate(view, entity);

        if (view.getPassword() != null) {
            validatePassword(view.getPassword(), view.getConfirmPassword());
        }

        userRepository.findByPhone(view.getPhone()).ifPresent(existing -> {
            if (!existing.getId().equals(entity.getId())) {
                throw new ConflictException("User with such phone already exists");
            }
        });
        if (view.getEmail() != null) {
            userRepository.findByEmail(view.getEmail()).ifPresent(existing -> {
                if (!existing.getId().equals(entity.getId())) {
                    throw new ConflictException("User with such email already exists");
                }
            });
        }
        userRepository.findByUsername(view.getUsername()).ifPresent(existing -> {
            if (!existing.getId().equals(entity.getId())) {
                throw new ConflictException("User with such username already exists");
            }
        });
    }

    private void validatePassword(String password, String confirmPassword) {
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
        }

        if (!(hasUpper && hasLower && hasDigit)) {
            throw new ValidationException("Password must contain upper and lower letters and digits");
        }

        if (!password.equals(confirmPassword)) {
            throw new ValidationException("Incorrect confirm password");
        }
    }
}
