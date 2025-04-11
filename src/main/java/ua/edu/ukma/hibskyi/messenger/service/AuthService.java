package ua.edu.ukma.hibskyi.messenger.service;

public interface AuthService {

    boolean isAuthenticated();

    String getAuthenticatedUserId();
}
