package com.example.automovel.dto;

import com.example.automovel.model.enums.StatusPedido;
import java.time.LocalDate;

public class PedidoResumoDTO {
    private Long id;
    private LocalDate dataPedido;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusPedido status;
    private Double valorTotal;

    public PedidoResumoDTO() {}

    public PedidoResumoDTO(Long id, LocalDate dataPedido, LocalDate dataInicio, 
                          LocalDate dataFim, StatusPedido status, Double valorTotal) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.valorTotal = valorTotal;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDate dataPedido) { this.dataPedido = dataPedido; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
}