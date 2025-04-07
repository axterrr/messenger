package ua.edu.ukma.hibskyi.messenger.mapper;

import java.util.List;

public abstract class BaseMapperImpl<ENTITY, VIEW, RESPONSE> implements BaseMapper<ENTITY, VIEW, RESPONSE> {

    public List<RESPONSE> mapToResponse(List<ENTITY> list) {
        return list.stream()
            .map(this::mapToResponse)
            .toList();
    }
}
