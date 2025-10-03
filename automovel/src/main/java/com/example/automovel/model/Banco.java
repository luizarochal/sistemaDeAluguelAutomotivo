package com.example.automovel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bancos")
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String centralTelefone;

    public Banco() {
    }

    public Banco(String codigo, String nome, String centralTelefone) {
        this.codigo = codigo;
        this.nome = nome;
        this.centralTelefone = centralTelefone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCentralTelefone() {
        return centralTelefone;
    }

    public void setCentralTelefone(String centralTelefone) {
        this.centralTelefone = centralTelefone;
    }
}