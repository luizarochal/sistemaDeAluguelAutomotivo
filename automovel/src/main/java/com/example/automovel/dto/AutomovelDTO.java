package com.example.automovel.dto;

import com.example.automovel.model.enums.TipoProprietario;

public class AutomovelDTO {
    private String matricula;
    private Integer ano;
    private String marca;
    private String modelo;
    private String placa;
    private TipoProprietario proprietario;
    private Boolean disponivel = true;
    private Long empresaProprietariaId;
    private Long bancoProprietarioId;

    // Construtores
    public AutomovelDTO() {
    }

    public AutomovelDTO(String matricula, Integer ano, String marca, String modelo,
            String placa, TipoProprietario proprietario, Boolean disponivel,
            Long empresaProprietariaId, Long bancoProprietarioId) {
        this.matricula = matricula;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.proprietario = proprietario;
        this.disponivel = disponivel;
        this.empresaProprietariaId = empresaProprietariaId;
        this.bancoProprietarioId = bancoProprietarioId;
    }

    // Getters e Setters
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

    public Long getEmpresaProprietariaId() {
        return empresaProprietariaId;
    }

    public void setEmpresaProprietariaId(Long empresaProprietariaId) {
        this.empresaProprietariaId = empresaProprietariaId;
    }

    public Long getBancoProprietarioId() {
        return bancoProprietarioId;
    }

    public void setBancoProprietarioId(Long bancoProprietarioId) {
        this.bancoProprietarioId = bancoProprietarioId;
    }
}