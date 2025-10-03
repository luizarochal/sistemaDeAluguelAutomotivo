package com.example.automovel.dto;

public class BancoDTO {
    private String codigo;
    private String nome;
    private String centralTelefone;

    public BancoDTO() {
    }

    public BancoDTO(String codigo, String nome, String centralTelefone) {
        this.codigo = codigo;
        this.nome = nome;
        this.centralTelefone = centralTelefone;
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