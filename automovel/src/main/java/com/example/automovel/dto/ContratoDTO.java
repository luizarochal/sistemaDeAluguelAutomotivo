package com.example.automovel.dto;

import com.example.automovel.model.enums.StatusContrato;
import java.time.LocalDate;

public class ContratoDTO {
    private Long id;
    private PedidoResumoDTO pedido;
    private ClienteDTO cliente;
    private AutomovelDTO automovel;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Double valorTotal;
    private Double valorEntrada;
    private String formaPagamento;
    private Integer diaVencimento;
    private StatusContrato status;
    private LocalDate dataCriacao;

    // Construtores
    public ContratoDTO() {}

    public ContratoDTO(Long id, PedidoResumoDTO pedido, ClienteDTO cliente, 
                      AutomovelDTO automovel, LocalDate dataInicio, LocalDate dataFim, 
                      Double valorTotal, Double valorEntrada, StatusContrato status) {
        this.id = id;
        this.pedido = pedido;
        this.cliente = cliente;
        this.automovel = automovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorTotal = valorTotal;
        this.valorEntrada = valorEntrada;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PedidoResumoDTO getPedido() { return pedido; }
    public void setPedido(PedidoResumoDTO pedido) { this.pedido = pedido; }

    public ClienteDTO getCliente() { return cliente; }
    public void setCliente(ClienteDTO cliente) { this.cliente = cliente; }

    public AutomovelDTO getAutomovel() { return automovel; }
    public void setAutomovel(AutomovelDTO automovel) { this.automovel = automovel; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public Double getValorEntrada() { return valorEntrada; }
    public void setValorEntrada(Double valorEntrada) { this.valorEntrada = valorEntrada; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }

    public Integer getDiaVencimento() { return diaVencimento; }
    public void setDiaVencimento(Integer diaVencimento) { this.diaVencimento = diaVencimento; }

    public StatusContrato getStatus() { return status; }
    public void setStatus(StatusContrato status) { this.status = status; }

    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
}