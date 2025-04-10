package ua.edu.ukma.hibskyi.messenger.validator;

public interface BaseValidator<ENTITY, VIEW, ID> {

    void validateForCreate(VIEW view);

    void validateForUpdate(VIEW view, ENTITY entity);

    void validateForDelete(ID id);
}
