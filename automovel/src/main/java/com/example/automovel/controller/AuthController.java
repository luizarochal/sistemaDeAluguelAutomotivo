package com.example.automovel.controller;

import com.example.automovel.model.RoleName;
import com.example.automovel.model.User;
import com.example.automovel.payload.auth.AuthResponse;
import com.example.automovel.payload.auth.LoginRequest;
import com.example.automovel.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Cadastro/Login", description = "Endpoints de Login e Cadastro de Usuários")
public class AuthController {

    @Autowired
    private AuthService authService;

    // --- Endpoint de LOGIN ---
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // --- Endpoint de CADASTRO ---
    @PostMapping("/cadastro")
    public ResponseEntity<String> registerUser(@RequestParam String username,
            @RequestParam String password,
            @RequestParam RoleName initialRole,
            @RequestParam String specificIdentifier) {

        try {
            User registeredUser = authService.registerUser(username, password, initialRole, specificIdentifier);

            String response = String.format(
                    "Usuário %s (%s) cadastrado com sucesso. ID: %d",
                    registeredUser.getUsername(), initialRole.name(), registeredUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }
}