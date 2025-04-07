package ua.edu.ukma.hibskyi.messenger.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import ua.edu.ukma.hibskyi.messenger.exception.NotFoundException;
import ua.edu.ukma.hibskyi.messenger.mapper.BaseMapper;
import ua.edu.ukma.hibskyi.messenger.entity.Identifiable;
import ua.edu.ukma.hibskyi.messenger.repository.BaseRepository;
import ua.edu.ukma.hibskyi.messenger.service.BaseService;
import ua.edu.ukma.hibskyi.messenger.validator.BaseValidator;

import java.util.List;

public abstract class BaseServiceImpl<ENTITY extends Identifiable<ID>, VIEW, RESPONSE, ID>
        implements BaseService<VIEW, RESPONSE, ID> {

    @Autowired
    protected BaseRepository<ENTITY, ID> repository;

    @Autowired
    protected BaseMapper<ENTITY, VIEW, RESPONSE> mapper;

    @Autowired
    protected BaseValidator<ENTITY> validator;

    @Override
    public List<RESPONSE> getAll() {
        List<ENTITY> result = repository.findAll();
        return mapper.mapToResponse(result);
    }

    @Override
    public RESPONSE getById(ID id) {
        ENTITY result = getEntityById(id);
        return mapper.mapToResponse(result);
    }

    @Override
    public ID create(VIEW view) {
        ENTITY entity = mapper.mapToEntity(view);
        validator.validateForCreate(entity);
        return repository.save(entity).getId();
    }

    @Override
    public void update(ID id, VIEW view) {
        ENTITY existing = getEntityById(id);
        ENTITY entity = mapper.mapToEntity(view);
        entity.setId(id);
        validator.validateForUpdate(entity);
        repository.save(entity);
    }

    @Override
    public void deleteById(ID id) {
        ENTITY entity = getEntityById(id);
        validator.validateForDelete(entity);
        repository.deleteById(id);
    }

    private ENTITY getEntityById(ID id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Entity with such id was not found"));
    }
}
