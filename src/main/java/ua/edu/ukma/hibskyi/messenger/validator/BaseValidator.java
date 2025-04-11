package ua.edu.ukma.hibskyi.messenger.validator;

public interface BaseValidator<ENTITY, VIEW> {

    void validateForViewAll();

    void validateForView(ENTITY entity);

    void validateForCreate(VIEW view);

    void validateForUpdate(VIEW view, ENTITY entity);

    void validateForDelete(ENTITY entity);
}
