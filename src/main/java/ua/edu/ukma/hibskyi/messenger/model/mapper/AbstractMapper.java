package ua.edu.ukma.hibskyi.messenger.model.mapper;

import java.util.List;

public abstract class AbstractMapper<V, R, E> {

    public abstract E mapToEntity(V view);

    public abstract R mapToResponse(E entity);

    public List<R> mapToResponse(List<E> list) {
        return list.stream()
            .map(this::mapToResponse)
            .toList();
    }
}
