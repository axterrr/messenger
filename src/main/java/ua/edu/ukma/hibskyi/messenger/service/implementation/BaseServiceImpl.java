package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import ua.edu.ukma.hibskyi.messenger.mapper.BaseMapper;
import ua.edu.ukma.hibskyi.messenger.model.entity.Identifiable;
import ua.edu.ukma.hibskyi.messenger.repository.BaseRepository;
import ua.edu.ukma.hibskyi.messenger.service.BaseService;

import java.util.List;

@AllArgsConstructor
public abstract class BaseServiceImpl<ENTITY extends Identifiable<ID>, VIEW, RESPONSE, ID>
        implements BaseService<VIEW, RESPONSE, ID> {

    protected final BaseRepository<ENTITY, ID> repository;
    protected final BaseMapper<ENTITY, VIEW, RESPONSE> mapper;

    @Override
    public List<RESPONSE> getAll() {
        List<ENTITY> result = repository.findAll();
        return mapper.mapToResponse(result);
    }

    @Override
    public RESPONSE getById(ID id) {
        ENTITY result = repository.findById(id).orElseThrow();
        return mapper.mapToResponse(result);
    }

    @Override
    public ID create(VIEW view) {
        ENTITY entity = mapper.mapToEntity(view);
        ENTITY result = repository.save(entity);
        return result.getId();
    }

    @Override
    public void update(ID id, VIEW view) {
        ENTITY entity = mapper.mapToEntity(view);
        entity.setId(id);
        repository.save(entity);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
