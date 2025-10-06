package com.example.automovel.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.example.automovel.model.enums.StatusContratoCredito;

@Entity
@Table(name = "contratos_credito")
@PrimaryKeyJoinColumn(name = "contrato_id")
public class ContratoCredito extends Contrato {

    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = false)
    private Banco banco;

    @Column(nullable = false, columnDefinition = "DOUBLE")
    private Double valorFinanciado;

    @Column(nullable = false)
    private Integer numeroParcelas;

    @Column(columnDefinition = "DOUBLE")
    private Double taxaJuros;

    @Column(nullable = false, columnDefinition = "DOUBLE")
    private Double valorParcela;

    @Column
    private LocalDate dataPrimeiroVencimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusContratoCredito statusCredito;

    public ContratoCredito() {
        super();
        this.statusCredito = StatusContratoCredito.ATIVO;
    }

    public ContratoCredito(Pedido pedido, Cliente cliente, Automovel automovel,
            LocalDate dataInicio, LocalDate dataFim, Double valorTotal,
            Double valorEntrada, String formaPagamento, Integer diaVencimento,
            Banco banco, Double valorFinanciado, Integer numeroParcelas,
            Double taxaJuros, LocalDate dataPrimeiroVencimento) {
        super(pedido, cliente, automovel, dataInicio, dataFim, valorTotal,
                valorEntrada, formaPagamento, diaVencimento);
        this.banco = banco;
        this.valorFinanciado = valorFinanciado;
        this.numeroParcelas = numeroParcelas;
        this.taxaJuros = taxaJuros;
        this.dataPrimeiroVencimento = dataPrimeiroVencimento;
        this.statusCredito = StatusContratoCredito.ATIVO;
        calcularValorParcela();
    }

    private void calcularValorParcela() {
        if (valorFinanciado != null && numeroParcelas != null && taxaJuros != null) {
            double juros = valorFinanciado * (taxaJuros / 100);
            this.valorParcela = (valorFinanciado + juros) / numeroParcelas;
        }
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Double getValorFinanciado() {
        return valorFinanciado;
    }

    public void setValorFinanciado(Double valorFinanciado) {
        this.valorFinanciado = valorFinanciado;
        calcularValorParcela();
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
        calcularValorParcela();
    }

    public Double getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(Double taxaJuros) {
        this.taxaJuros = taxaJuros;
        calcularValorParcela();
    }

    public Double getValorParcela() {
        return valorParcela;
    }

    public LocalDate getDataPrimeiroVencimento() {
        return dataPrimeiroVencimento;
    }

    public void setDataPrimeiroVencimento(LocalDate dataPrimeiroVencimento) {
        this.dataPrimeiroVencimento = dataPrimeiroVencimento;
    }

    public StatusContratoCredito getStatusCredito() {
        return statusCredito;
    }

    public void setStatusCredito(StatusContratoCredito statusCredito) {
        this.statusCredito = statusCredito;
    }
}
