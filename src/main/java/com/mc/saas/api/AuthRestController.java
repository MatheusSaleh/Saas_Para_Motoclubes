package com.mc.saas.api;

import com.mc.saas.api.dto.LoginRequest;
import com.mc.saas.api.dto.LoginResponse;
import com.mc.saas.api.dto.UpdatePhotoRequest;
import com.mc.saas.api.dto.UserResponse;
import com.mc.saas.config.JwtService;
import com.mc.saas.domain.user.User;
import com.mc.saas.domain.user.UserGateway;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserGateway userGateway;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userGateway.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + authentication.getName()));

        String token = jwtService.generateToken(user.getEmail(), user.getId(), user.getName());
        long expiresIn = jwtService.getExpirationMs() / 1000L;

        return new LoginResponse(
                token,
                "Bearer",
                expiresIn,
                toResponse(user)
        );
    }

    @GetMapping("/me")
    public UserResponse me() {
        return toResponse(currentUser());
    }

    @PutMapping("/me/photo")
    public UserResponse updatePhoto(@RequestBody UpdatePhotoRequest request) {
        User current = currentUser();
        String photo = request.photo();
        if (photo != null && photo.isBlank()) {
            photo = null;
        }
        return toResponse(userGateway.updatePhoto(current.getId(), photo));
    }

    private User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Sem usuário autenticado.");
        }
        return userGateway.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + authentication.getName()));
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhoto());
    }
}
