package ua.edu.ukma.hibskyi.messenger.mapper;

import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class MapperUtils {

    public static <T, R> List<R> mapIfInitialized(Collection<T> collection, Function<T, R> mapper) {
        if (collection != null && Hibernate.isInitialized(collection)) {
            return collection.stream().map(mapper).toList();
        }
        return new ArrayList<>();
    }

    public static <T> void ifNotNull(T value, Consumer<T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }
}
