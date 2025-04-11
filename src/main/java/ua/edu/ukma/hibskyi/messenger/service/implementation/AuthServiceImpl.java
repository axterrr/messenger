package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.dto.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.exception.NotFoundException;
import ua.edu.ukma.hibskyi.messenger.mapper.UserMapper;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;
import ua.edu.ukma.hibskyi.messenger.service.AuthService;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
    }

    @Override
    public UserResponse getAuthenticatedUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity entity = userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User with such username was not found"));
        return userMapper.mapToResponse(entity);
    }
}
