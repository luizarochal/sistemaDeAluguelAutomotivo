package com.example.automovel.service;

import com.example.automovel.model.Role;
import com.example.automovel.model.RoleName;
import com.example.automovel.model.User;
import com.example.automovel.payload.auth.AuthResponse;
import com.example.automovel.repository.RoleRepository;
import com.example.automovel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // Método de cadastro manual
    public User registerUser(String username, String password, RoleName initialRole, String specificIdentifier) {

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Erro: Nome de usuário já está em uso.");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        Role role = roleRepository.findByName(initialRole)
                .orElseThrow(() -> new RuntimeException("Erro: Role não encontrada."));

        Set<Role> roles = new HashSet<>(Collections.singletonList(role));
        user.setRoles(roles);

        if (initialRole == RoleName.ROLE_CLIENTE) {
            user.setCpfCliente(specificIdentifier);
        } else if (initialRole == RoleName.ROLE_BANCO) {
            user.setCodigoBanco(specificIdentifier);
        } else if (initialRole == RoleName.ROLE_EMPRESA) {
            user.setCnpjEmpresa(specificIdentifier);
        }

        return userRepository.save(user);
    }

    public AuthResponse login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos."));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Usuário ou senha inválidos.");
        }

        Role role = user.getRoles().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Usuário sem permissão atribuída."));

        RoleName roleName = role.getName();
        String identifier = null;

        if (roleName == RoleName.ROLE_CLIENTE) {
            identifier = user.getCpfCliente();
        } else if (roleName == RoleName.ROLE_BANCO) {
            identifier = user.getCodigoBanco();
        } else if (roleName == RoleName.ROLE_EMPRESA) {
            identifier = user.getCnpjEmpresa();
        }

        return new AuthResponse(user.getId(), user.getUsername(), roleName, identifier);
    }
}