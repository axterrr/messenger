package ua.edu.ukma.hibskyi.messenger.mapper;

import java.util.List;

public abstract class BaseMapperImpl<ENTITY, VIEW, RESPONSE> implements BaseMapper<ENTITY, VIEW, RESPONSE> {

    public abstract ENTITY mapToEntity(VIEW view);

    public abstract RESPONSE mapToResponse(ENTITY entity);

    public List<RESPONSE> mapToResponse(List<ENTITY> list) {
        return list.stream()
            .map(this::mapToResponse)
            .toList();
    }
}
