package ua.edu.ukma.hibskyi.messenger.security.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.edu.ukma.hibskyi.messenger.security.JWTUtility;

import java.io.IOException;
import java.util.Map;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtility jwtUtility;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JWTUtility jwtUtility) {
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        Map<String, String> body;
        try {
            body = new ObjectMapper().readValue(request.getInputStream(), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || username.isBlank()) {
            throw new BadCredentialsException("Username cannot be empty.");
        }
        if (password == null || password.isBlank()) {
            throw new BadCredentialsException("Password cannot be empty.");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException {
        String token = jwtUtility.getToken(authResult.getName(), authResult.getAuthorities());
        response.addCookie(new Cookie("SECRET_TOKEN", token));
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }
}
