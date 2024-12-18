package org.ensa.serviceutilisateurs.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ensa.serviceutilisateurs.jwt.JwtUtil;
import org.ensa.serviceutilisateurs.services.UtilisateursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UtilisateursService utilisateursService;

    /**
     * Endpoint to authenticate a user and return JWT tokens (access and refresh tokens).
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Authenticate the user using the provided credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Load user details after successful authentication
        UserDetails userDetails = utilisateursService.loadUserByUsername(authRequest.getUsername());

        // Extract roles from the UserDetails
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // Generate JWT tokens
        String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername(), roles);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        // Return tokens in the response
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    /**
     * Endpoint to refresh the access token using a valid refresh token.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();

        // Extract the username from the refresh token
        String username = jwtUtil.extractUsername(refreshToken);

        // Validate the refresh token
        if (username == null || !jwtUtil.validateToken(refreshToken, username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        // Load user details to regenerate roles
        UserDetails userDetails = utilisateursService.loadUserByUsername(username);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // Generate a new access token
        String newAccessToken = jwtUtil.generateAccessToken(username, roles);

        // Return the new access token (refresh token remains the same)
        return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshToken));
    }
}

@Data
@AllArgsConstructor
class AuthRequest {
    private String username;
    private String password;
}

@Data
@AllArgsConstructor
class AuthResponse {
    private String accessToken;
    private String refreshToken;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class RefreshRequest {
    private String refreshToken;
}

