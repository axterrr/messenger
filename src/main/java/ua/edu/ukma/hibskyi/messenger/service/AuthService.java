package ua.edu.ukma.hibskyi.messenger.service;

import ua.edu.ukma.hibskyi.messenger.dto.response.UserResponse;

public interface AuthService {

    boolean isAuthenticated();

    UserResponse getAuthenticatedUser();
}
