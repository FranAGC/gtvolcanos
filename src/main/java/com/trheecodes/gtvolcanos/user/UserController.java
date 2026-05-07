package com.trheecodes.gtvolcanos.user;

import com.trheecodes.gtvolcanos.shared.response.ApiResponse;
import com.trheecodes.gtvolcanos.user.dto.CreateUserRequest;
import com.trheecodes.gtvolcanos.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * POST /api/users
     * Crea un nuevo usuario con contraseña hasheada.
     * Requiere autenticación (solo admin debería poder crear usuarios).
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse user = userService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("Usuario creado exitosamente", user));
    }

    /**
     * GET /api/users/{id}
     * Obtiene un usuario por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.ok("Usuario encontrado", userService.getUserById(id)));
    }
}
