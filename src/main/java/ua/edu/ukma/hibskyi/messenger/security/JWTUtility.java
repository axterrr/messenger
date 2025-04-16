package ua.edu.ukma.hibskyi.messenger.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Component
public class JWTUtility {

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    @Value("${JWT_TOKEN_EXPIRATION_TIME}")
    private long expirationTime;

    public String getToken(String id, Collection<? extends GrantedAuthority> authorities) {
        return JWT.create()
            .withSubject(id)
            .withClaim("roles", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList())
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
            .sign(Algorithm.HMAC512(secretKey));
    }

    public DecodedJWT verifyToken(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC512(secretKey))
            .build()
            .verify(token);
    }
}
