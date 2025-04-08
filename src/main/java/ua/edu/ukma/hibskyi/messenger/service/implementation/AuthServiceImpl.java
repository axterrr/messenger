package ua.edu.ukma.hibskyi.messenger.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.edu.ukma.hibskyi.messenger.dto.response.UserResponse;
import ua.edu.ukma.hibskyi.messenger.service.AuthService;
import ua.edu.ukma.hibskyi.messenger.service.UserService;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserService userService;

    @Override
    public UserResponse getAuthenticatedUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getByUsername(username);
    }
}
