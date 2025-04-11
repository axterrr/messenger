package ua.edu.ukma.hibskyi.messenger.mapper;

import org.hibernate.Hibernate;
import ua.edu.ukma.hibskyi.messenger.entity.ChatEntity;
import ua.edu.ukma.hibskyi.messenger.entity.MessageEntity;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;

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

    public static UserEntity userEntityFromId(String id) {
        return UserEntity.builder().id(id).build();
    }

    public static ChatEntity chatEntityFromId(String id) {
        return ChatEntity.builder().id(id).build();
    }

    public static MessageEntity messageEntityFromId(String id) {
        return MessageEntity.builder().id(id).build();
    }
}
