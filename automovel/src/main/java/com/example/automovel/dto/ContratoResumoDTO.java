package com.example.automovel.dto;

import com.example.automovel.model.enums.StatusContrato;
import java.time.LocalDate;

public class ContratoResumoDTO {
    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Double valorTotal;
    private StatusContrato status;

    public ContratoResumoDTO() {}

    public ContratoResumoDTO(Long id, LocalDate dataInicio, LocalDate dataFim, 
                            Double valorTotal, StatusContrato status) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public StatusContrato getStatus() { return status; }
    public void setStatus(StatusContrato status) { this.status = status; }
}