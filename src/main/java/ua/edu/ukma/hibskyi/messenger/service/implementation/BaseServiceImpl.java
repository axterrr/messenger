package ua.edu.ukma.hibskyi.messenger.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ukma.hibskyi.messenger.exception.NotFoundException;
import ua.edu.ukma.hibskyi.messenger.mapper.BaseMapper;
import ua.edu.ukma.hibskyi.messenger.entity.Identifiable;
import ua.edu.ukma.hibskyi.messenger.repository.BaseRepository;
import ua.edu.ukma.hibskyi.messenger.service.BaseService;
import ua.edu.ukma.hibskyi.messenger.validator.BaseValidator;

import java.util.List;

@Transactional
public abstract class BaseServiceImpl<ENTITY extends Identifiable<ID>, VIEW, RESPONSE, ID>
        implements BaseService<VIEW, RESPONSE, ID> {

    @Autowired
    protected BaseRepository<ENTITY, ID> repository;

    @Autowired
    protected BaseMapper<ENTITY, VIEW, RESPONSE> mapper;

    @Autowired
    protected BaseValidator<ENTITY, VIEW> validator;

    @Override
    public List<RESPONSE> getAll() {
        validator.validateForViewAll();
        List<ENTITY> result = repository.findAll();
        return mapper.mapToResponse(result);
    }

    @Override
    public RESPONSE getById(ID id) {
        ENTITY entity = getEntityById(id);
        validator.validateForView(entity);
        return mapper.mapToResponse(entity);
    }

    @Override
    public ID create(VIEW view) {
        validator.validateForCreate(view);
        ENTITY entity = mapper.mapToEntity(view);
        return repository.save(entity).getId();
    }

    @Override
    public void update(ID id, VIEW view) {
        ENTITY entity = getEntityById(id);
        validator.validateForUpdate(view, entity);
        mapper.merge(view, entity);
        repository.save(entity);
    }

    @Override
    public void deleteById(ID id) {
        ENTITY entity = getEntityById(id);
        validator.validateForDelete(entity);
        repository.deleteById(id);
    }

    protected ENTITY getEntityById(ID id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Entity with such id was not found"));
    }
}
