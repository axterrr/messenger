package ua.edu.ukma.hibskyi.messenger.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.model.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements GenericService<UserEntity, UUID> {

    private UserRepository userRepository;

    @Override
    public UserEntity getById(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public UUID create(UserEntity entity) {
        return userRepository.save(entity).getId();
    }

    @Override
    public void update(UUID id, UserEntity entity) {
        entity.setId(id);
        userRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
