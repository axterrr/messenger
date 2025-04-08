package ua.edu.ukma.hibskyi.messenger.security.manager;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.edu.ukma.hibskyi.messenger.entity.UserEntity;
import ua.edu.ukma.hibskyi.messenger.repository.UserRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<UserEntity> user = userRepository.findByUsername(authentication.getPrincipal().toString());
        if (user.isEmpty() || !passwordEncoder.matches(authentication.getCredentials().toString(), user.get().getPasswordHash())) {
            throw new BadCredentialsException("Bad credentials");
        }
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), user.get().getPasswordHash());
    }
}
