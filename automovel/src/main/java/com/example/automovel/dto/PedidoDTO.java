package com.example.automovel.dto;

import jakarta.validation.constraints.*;
import java.util.Objects;

public class PedidoDTO {

    @NotNull
    private Long automovelId;

    @NotNull
    private Long clienteId;

    private Double valorEstimado;

    @Size(max = 1000)
    private String observacoes;

    public Long getAutomovelId() {
        return automovelId;
    }

    public void setAutomovelId(Long automovelId) {
        this.automovelId = automovelId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Double getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(Double valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PedidoDTO)) return false;
        PedidoDTO pedidoDTO = (PedidoDTO) o;
        return Objects.equals(automovelId, pedidoDTO.automovelId) && Objects.equals(clienteId, pedidoDTO.clienteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(automovelId, clienteId);
    }
}
