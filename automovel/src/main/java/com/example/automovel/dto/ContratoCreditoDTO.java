package com.example.automovel.dto;

import com.example.automovel.model.enums.StatusContratoCredito;
import java.time.LocalDate;

public class ContratoCreditoDTO extends ContratoDTO {
    private BancoDTO banco;
    private Double valorFinanciado;
    private Integer numeroParcelas;
    private Double taxaJuros;
    private Double valorParcela;
    private LocalDate dataPrimeiroVencimento;
    private StatusContratoCredito statusCredito;

    // Construtores
    public ContratoCreditoDTO() {}

    public ContratoCreditoDTO(Long id, PedidoResumoDTO pedido, ClienteDTO cliente, 
                             AutomovelDTO automovel, LocalDate dataInicio, LocalDate dataFim, 
                             Double valorTotal, Double valorEntrada, BancoDTO banco,
                             Double valorFinanciado, Integer numeroParcelas, Double taxaJuros) {
        super(id, pedido, cliente, automovel, dataInicio, dataFim, valorTotal, valorEntrada, null);
        this.banco = banco;
        this.valorFinanciado = valorFinanciado;
        this.numeroParcelas = numeroParcelas;
        this.taxaJuros = taxaJuros;
    }

    // Getters e Setters
    public BancoDTO getBanco() { return banco; }
    public void setBanco(BancoDTO banco) { this.banco = banco; }

    public Double getValorFinanciado() { return valorFinanciado; }
    public void setValorFinanciado(Double valorFinanciado) { this.valorFinanciado = valorFinanciado; }

    public Integer getNumeroParcelas() { return numeroParcelas; }
    public void setNumeroParcelas(Integer numeroParcelas) { this.numeroParcelas = numeroParcelas; }

    public Double getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(Double taxaJuros) { this.taxaJuros = taxaJuros; }

    public Double getValorParcela() { return valorParcela; }
    public void setValorParcela(Double valorParcela) { this.valorParcela = valorParcela; }

    public LocalDate getDataPrimeiroVencimento() { return dataPrimeiroVencimento; }
    public void setDataPrimeiroVencimento(LocalDate dataPrimeiroVencimento) { this.dataPrimeiroVencimento = dataPrimeiroVencimento; }

    public StatusContratoCredito getStatusCredito() { return statusCredito; }
    public void setStatusCredito(StatusContratoCredito statusCredito) { this.statusCredito = statusCredito; }
}