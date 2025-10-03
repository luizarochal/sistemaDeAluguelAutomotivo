package com.example.automovel.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String CNPJ;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String telefone;

    @ElementCollection
    @CollectionTable(name = "empresa_socios", joinColumns = @JoinColumn(name = "empresa_id"))
    @Column(name = "socio")
    private List<String> socios;

    public Empresa() {
    }

    public Empresa(String CNPJ, String nome, String endereco, String telefone, List<String> socios) {
        this.CNPJ = CNPJ;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.socios = socios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<String> getSocios() {
        return socios;
    }

    public void setSocios(List<String> socios) {
        this.socios = socios;
    }
}