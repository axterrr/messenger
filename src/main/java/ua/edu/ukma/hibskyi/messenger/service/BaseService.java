package ua.edu.ukma.hibskyi.messenger.service;

import java.util.List;

public interface BaseService<V, R, I> {

    List<R> getAll();

    R getById(I id);

    I create(V view);

    void update(I id, V view);

    void deleteById(I id);
}
