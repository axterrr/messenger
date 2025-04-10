package ua.edu.ukma.hibskyi.messenger.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ukma.hibskyi.messenger.exception.ValidationException;
import ua.edu.ukma.hibskyi.messenger.entity.Identifiable;
import ua.edu.ukma.hibskyi.messenger.repository.BaseRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Transactional
public abstract class BaseValidatorImpl<ENTITY extends Identifiable<ID>, VIEW, ID> implements BaseValidator<ENTITY, VIEW, ID> {

    @Autowired
    private Validator validator;

    @Autowired
    private BaseRepository<ENTITY, ID> repository;

    @Override
    public void validateForCreate(VIEW view) {
        Set<ConstraintViolation<VIEW>> violations = validator.validate(view);
        if (!violations.isEmpty()) {
            throwException(violations);
        }
    }

    @Override
    public void validateForUpdate(VIEW view, ENTITY entity) {
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
    public void validateForDelete(ID id) {
        if (!repository.existsById(id)) {
            throw new ValidationException("Cannot delete non existing entity");
        }
    }

    private void throwException(Set<ConstraintViolation<VIEW>> violations) {
        throw new ValidationException(violations.stream()
            .map(ConstraintViolation::getMessage)
            .toList()
        );
    }
}
