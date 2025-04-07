package ua.edu.ukma.hibskyi.messenger.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ukma.hibskyi.messenger.exception.ValidationException;
import ua.edu.ukma.hibskyi.messenger.entity.Identifiable;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseValidatorImpl<ENTITY extends Identifiable<ID>, ID> implements BaseValidator<ENTITY> {

    @Autowired
    private Validator validator;

    @Override
    public void validateForCreate(ENTITY entity) {
        Set<ConstraintViolation<ENTITY>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            throwException(violations);
        }

        if (entity.getId() != null) {
            throw new ValidationException("Cannot create entity with id");
        }
    }

    @Override
    public void validateForUpdate(ENTITY entity) {
        Set<ConstraintViolation<ENTITY>> violations = validator.validate(entity).stream()
            .filter(violation -> violation.getInvalidValue() != null)
            .collect(Collectors.toSet());
        if (!violations.isEmpty()) {
            throwException(violations);
        }

        if (entity.getId() == null) {
            throw new ValidationException("Cannot update entity without id");
        }
    }

    @Override
    public void validateForDelete(ENTITY entity) {

    }

    private void throwException(Set<ConstraintViolation<ENTITY>> violations) {
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<ENTITY> violation : violations) {
            errorMessage.append(violation.getMessage()).append("; ");
        }
        throw new ValidationException(errorMessage.toString());
    }
}
