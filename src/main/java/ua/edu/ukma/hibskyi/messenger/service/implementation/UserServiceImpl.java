package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.model.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.model.mapper.UserMapper;
import ua.edu.ukma.hibskyi.messenger.model.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.model.view.UserView;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;
import ua.edu.ukma.hibskyi.messenger.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public List<UserResponse> getAll() {
        List<UserEntity> result = userRepository.findAll();
        return userMapper.mapToResponse(result);
    }

    @Override
    public UserResponse getById(String id) {
        UserEntity result = userRepository.findById(UUID.fromString(id)).orElseThrow();
        return userMapper.mapToResponse(result);
    }

    @Override
    public String create(UserView view) {
        UserEntity entity = userMapper.mapToEntity(view);
        UUID id = userRepository.save(entity).getId();
        return id.toString();
    }

    @Override
    public void update(String id, UserView view) {
        UserEntity entity = userMapper.mapToEntity(view);
        entity.setId(UUID.fromString(id));
        userRepository.save(entity);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(UUID.fromString(id));
    }
}
