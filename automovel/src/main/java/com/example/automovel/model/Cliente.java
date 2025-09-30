package com.example.automovel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String RG;
    
    @Column(nullable = false, unique = true)
    private String CPF;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String endereco;
    
    private String profissao;
    
    @ElementCollection
    @CollectionTable(name = "cliente_empregadoras", joinColumns = @JoinColumn(name = "cliente_id"))
    @Column(name = "empregadora")
    private List<String> entidadesEmpregadoras;
    
    // Construtores
    public Cliente() {}
    
    public Cliente(String RG, String CPF, String nome, String endereco, String profissao, List<String> entidadesEmpregadoras) {
        this.RG = RG;
        this.CPF = CPF;
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
        this.entidadesEmpregadoras = entidadesEmpregadoras;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
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