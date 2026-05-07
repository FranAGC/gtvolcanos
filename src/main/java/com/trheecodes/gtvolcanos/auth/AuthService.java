package com.trheecodes.gtvolcanos.auth;

import com.trheecodes.gtvolcanos.auth.dto.AuthResponse;
import com.trheecodes.gtvolcanos.auth.dto.LoginRequest;
import com.trheecodes.gtvolcanos.auth.jwt.JwtService;
import com.trheecodes.gtvolcanos.shared.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    public AuthResponse login(LoginRequest request) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(user);
            return new AuthResponse(token, jwtExpiration);

        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new UnauthorizedException("Credenciales inválidas");
        }
    }
}
