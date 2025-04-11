package ua.edu.ukma.hibskyi.messenger.mapper;

import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public abstract class BaseMapperImpl<ENTITY, VIEW, RESPONSE> implements BaseMapper<ENTITY, VIEW, RESPONSE> {

    public List<RESPONSE> mapToResponse(List<ENTITY> list) {
        return list.stream()
            .map(this::mapToResponse)
            .toList();
    }

    protected static <T, R> List<R> mapIfInitialized(Collection<T> collection, Function<T, R> mapper) {
        if (collection != null && Hibernate.isInitialized(collection)) {
            return collection.stream().map(mapper).toList();
        }
        return new ArrayList<>();
    }
}
