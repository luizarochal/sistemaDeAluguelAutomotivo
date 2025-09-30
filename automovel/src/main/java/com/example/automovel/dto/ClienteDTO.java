package com.example.automovel.dto;

import java.util.List;

public class ClienteDTO {
    private String RG;
    private String CPF;
    private String nome;
    private String endereco;
    private String profissao;
    private List<String> entidadesEmpregadoras;
    
    // Construtores
    public ClienteDTO() {}
    
    public ClienteDTO(String RG, String CPF, String nome, String endereco, String profissao, List<String> entidadesEmpregadoras) {
        this.RG = RG;
        this.CPF = CPF;
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
        this.entidadesEmpregadoras = entidadesEmpregadoras;
    }
    
    // Getters e Setters
    public String getRG() { return RG; }
    public void setRG(String RG) { this.RG = RG; }
    
    public String getCPF() { return CPF; }
    public void setCPF(String CPF) { this.CPF = CPF; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }
    
    public List<String> getEntidadesEmpregadoras() { return entidadesEmpregadoras; }
    public void setEntidadesEmpregadoras(List<String> entidadesEmpregadoras) { 
        this.entidadesEmpregadoras = entidadesEmpregadoras; 
    }
}