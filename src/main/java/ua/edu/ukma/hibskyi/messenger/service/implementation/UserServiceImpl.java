package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.dto.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.dto.view.UserView;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.exception.NotFoundException;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;
import ua.edu.ukma.hibskyi.messenger.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserEntity, UserView, UserResponse, String> implements UserService {

    private UserRepository userRepository;

    public UserResponse getByUsername(String username) {
        UserEntity entity = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User with such username was not found"));
        return mapper.mapToResponse(entity);
    }
}
