package com.example.automovel.model;

import jakarta.persistence.*;
import com.example.automovel.model.enums.TipoProprietario;

@Entity
@Table(name = "automoveis")
public class Automovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProprietario proprietario;

    @Column(nullable = false)
    private Boolean disponivel = true;

    @ManyToOne
    @JoinColumn(name = "empresa_proprietaria_id")
    private Empresa empresaProprietaria;

    @ManyToOne
    @JoinColumn(name = "banco_proprietario_id")
    private Banco bancoProprietario;

    public Automovel() {
    }

    public Automovel(String matricula, Integer ano, String marca, String modelo,
            String placa, TipoProprietario proprietario) {
        this.matricula = matricula;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.proprietario = proprietario;
        this.disponivel = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public TipoProprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(TipoProprietario proprietario) {
        this.proprietario = proprietario;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Empresa getEmpresaProprietaria() {
        return empresaProprietaria;
    }

    public void setEmpresaProprietaria(Empresa empresaProprietaria) {
        this.empresaProprietaria = empresaProprietaria;
    }

    public Banco getBancoProprietario() {
        return bancoProprietario;
    }

    public void setBancoProprietario(Banco bancoProprietario) {
        this.bancoProprietario = bancoProprietario;
    }
}