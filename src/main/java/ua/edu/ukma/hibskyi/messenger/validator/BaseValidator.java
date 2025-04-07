package ua.edu.ukma.hibskyi.messenger.validator;

public interface BaseValidator<ENTITY> {

    void validateForCreate(ENTITY entity);

    void validateForUpdate(ENTITY entity);

    void validateForDelete(ENTITY entity);
}
