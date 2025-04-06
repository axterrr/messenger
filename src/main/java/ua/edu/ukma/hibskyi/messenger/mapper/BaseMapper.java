package ua.edu.ukma.hibskyi.messenger.mapper;

import java.util.List;

public interface BaseMapper<ENTITY, VIEW, RESPONSE> {
    ENTITY mapToEntity(VIEW view);

    RESPONSE mapToResponse(ENTITY entity);

    List<RESPONSE> mapToResponse(List<ENTITY> list);
}
