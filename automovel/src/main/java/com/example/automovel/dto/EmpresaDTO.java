package com.example.automovel.dto;

import java.util.List;

public class EmpresaDTO {
    private String CNPJ;
    private String nome;
    private String endereco;
    private String telefone;
    private List<String> socios;

    public EmpresaDTO() {
    }

    public EmpresaDTO(String CNPJ, String nome, String endereco, String telefone, List<String> socios) {
        this.CNPJ = CNPJ;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.socios = socios;
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