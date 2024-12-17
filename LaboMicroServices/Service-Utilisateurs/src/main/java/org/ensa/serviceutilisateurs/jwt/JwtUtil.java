package org.ensa.serviceutilisateurs.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "your_secret_key";

    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public String generateAccessToken(String username, List<String> roles) {
        return JWT.create()
                .withSubject(username)
                .withClaim("roles", roles) // Add roles as a custom claim
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() +1000 * 60 * 60 * 24 * 20)) // 15 minutes
                .sign(algorithm);
    }

    public String generateRefreshToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days
                .sign(algorithm);
    }

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
}
