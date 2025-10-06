package com.example.automovel.dto;

public class EstatisticasContratosDTO {
    private final int totalContratos;
    private final int totalContratosCredito;
    private final long contratosAtivos;
    private final double valorTotalContratos;
    private final double valorTotalFinanciado;

    public EstatisticasContratosDTO(int totalContratos, int totalContratosCredito, long contratosAtivos,
            double valorTotalContratos, double valorTotalFinanciado) {
        this.totalContratos = totalContratos;
        this.totalContratosCredito = totalContratosCredito;
        this.contratosAtivos = contratosAtivos;
        this.valorTotalContratos = valorTotalContratos;
        this.valorTotalFinanciado = valorTotalFinanciado;
    }

    public int getTotalContratos() {
        return totalContratos;
    }

    public int getTotalContratosCredito() {
        return totalContratosCredito;
    }

    public long getContratosAtivos() {
        return contratosAtivos;
    }

    public double getValorTotalContratos() {
        return valorTotalContratos;
    }

    public double getValorTotalFinanciado() {
        return valorTotalFinanciado;
    }
}
