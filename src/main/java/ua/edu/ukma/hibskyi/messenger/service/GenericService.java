package ua.edu.ukma.hibskyi.messenger.service;

public interface GenericService<E, I> {
    E getById(I id);
    I create(E entity);
    void update(I id, E entity);
    void deleteById(I id);
}
