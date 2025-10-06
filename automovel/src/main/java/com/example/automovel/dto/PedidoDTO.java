package com.example.automovel.dto;

import com.example.automovel.model.enums.StatusPedido;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {
    private Long id;
    private ClienteDTO cliente;
    private AutomovelDTO automovel;
    private LocalDate dataPedido;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusPedido status;
    private Double valorDiaria;
    private Double valorTotal;
    private String parecerFinanceiro;
    private BancoDTO bancoAvaliador;
    private LocalDateTime dataAvaliacao;
    private LocalDateTime dataModificacao;
    private List<String> historicoAlteracoes;
    private ContratoResumoDTO contrato;

    // Construtores
    public PedidoDTO() {}

    public PedidoDTO(Long id, ClienteDTO cliente, AutomovelDTO automovel, 
                    LocalDate dataPedido, LocalDate dataInicio, LocalDate dataFim, 
                    StatusPedido status, Double valorDiaria, Double valorTotal) {
        this.id = id;
        this.cliente = cliente;
        this.automovel = automovel;
        this.dataPedido = dataPedido;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.valorDiaria = valorDiaria;
        this.valorTotal = valorTotal;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ClienteDTO getCliente() { return cliente; }
    public void setCliente(ClienteDTO cliente) { this.cliente = cliente; }

    public AutomovelDTO getAutomovel() { return automovel; }
    public void setAutomovel(AutomovelDTO automovel) { this.automovel = automovel; }

    public LocalDate getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDate dataPedido) { this.dataPedido = dataPedido; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }

    public Double getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(Double valorDiaria) { this.valorDiaria = valorDiaria; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public String getParecerFinanceiro() { return parecerFinanceiro; }
    public void setParecerFinanceiro(String parecerFinanceiro) { this.parecerFinanceiro = parecerFinanceiro; }

    public BancoDTO getBancoAvaliador() { return bancoAvaliador; }
    public void setBancoAvaliador(BancoDTO bancoAvaliador) { this.bancoAvaliador = bancoAvaliador; }

    public LocalDateTime getDataAvaliacao() { return dataAvaliacao; }
    public void setDataAvaliacao(LocalDateTime dataAvaliacao) { this.dataAvaliacao = dataAvaliacao; }

    public LocalDateTime getDataModificacao() { return dataModificacao; }
    public void setDataModificacao(LocalDateTime dataModificacao) { this.dataModificacao = dataModificacao; }

    public List<String> getHistoricoAlteracoes() { return historicoAlteracoes; }
    public void setHistoricoAlteracoes(List<String> historicoAlteracoes) { this.historicoAlteracoes = historicoAlteracoes; }

    public ContratoResumoDTO getContrato() { return contrato; }
    public void setContrato(ContratoResumoDTO contrato) { this.contrato = contrato; }
}
