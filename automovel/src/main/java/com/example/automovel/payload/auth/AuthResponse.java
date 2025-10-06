package com.example.automovel.payload.auth;

import com.example.automovel.model.RoleName;

public class AuthResponse {

    private Long id;
    private String username;
    private RoleName role;
    private String specificIdentifier;

    public AuthResponse(Long id, String username, RoleName role, String specificIdentifier) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.specificIdentifier = specificIdentifier;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    public String getSpecificIdentifier() {
        return specificIdentifier;
    }

    public void setSpecificIdentifier(String specificIdentifier) {
        this.specificIdentifier = specificIdentifier;
    }
}