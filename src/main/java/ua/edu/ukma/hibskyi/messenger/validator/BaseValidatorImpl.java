package ua.edu.ukma.hibskyi.messenger.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ukma.hibskyi.messenger.entity.Identifiable;
import ua.edu.ukma.hibskyi.messenger.exception.ForbiddenException;
import ua.edu.ukma.hibskyi.messenger.exception.ValidationException;
import ua.edu.ukma.hibskyi.messenger.service.AuthService;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseValidatorImpl<ENTITY extends Identifiable<ID>, VIEW, ID> implements BaseValidator<ENTITY, VIEW> {

    @Autowired
    private Validator validator;

    @Autowired
    protected AuthService authService;

    @Override
    public void validateForView(ENTITY entity) {
        validatePermissionForView(entity);
    }

    @Override
    public void validateForCreate(VIEW view) {
        validatePermissionForCreate(view);

        Set<ConstraintViolation<VIEW>> violations = validator.validate(view);
        if (!violations.isEmpty()) {
            throwException(violations);
        }
    }

    @Override
    public void validateForUpdate(VIEW view, ENTITY entity) {
        validatePermissionForUpdate(entity);

        Set<ConstraintViolation<VIEW>> violations = validator.validate(view).stream()
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
        validatePermissionForDelete(entity);
    }

    private void throwException(Set<ConstraintViolation<VIEW>> violations) {
        throw new ValidationException(violations.stream()
            .map(ConstraintViolation::getMessage)
            .toList()
        );
    }

    @Override
    public void validateForViewAll() {
        throw new ForbiddenException();
    }

    protected abstract void validatePermissionForView(ENTITY entity);

    protected abstract void validatePermissionForCreate(VIEW view);

    protected abstract void validatePermissionForUpdate(ENTITY entity);

    protected abstract void validatePermissionForDelete(ENTITY entity);
}
