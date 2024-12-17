package org.ensa.serviceexamination.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "my_secret_key";

    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public String extractUsername(String token) {
        return decodeToken(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        return decodeToken(token).getClaim("roles").asList(String.class);
    }

    public boolean validateToken(String token, String username) {
        try {
            DecodedJWT decodedJWT = decodeToken(token);
            return decodedJWT.getSubject().equals(username) && !isTokenExpired(decodedJWT);
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private boolean isTokenExpired(DecodedJWT decodedJWT) {
        return decodedJWT.getExpiresAt().before(new Date());
    }

    private DecodedJWT decodeToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public Authentication getAuthentication(String token) {
        String username = extractUsername(token);
        List<String> roles = extractRoles(token);

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, token, authorities);
    }
}
