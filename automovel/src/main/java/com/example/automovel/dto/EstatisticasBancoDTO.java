package com.example.automovel.dto;

public class EstatisticasBancoDTO {
    private final double totalFinanciado;
    private final int totalContratos;
    private final long contratosAtivos;

    public EstatisticasBancoDTO(double totalFinanciado, int totalContratos, long contratosAtivos) {
        this.totalFinanciado = totalFinanciado;
        this.totalContratos = totalContratos;
        this.contratosAtivos = contratosAtivos;
    }
    
    public double getTotalFinanciado() {
        return totalFinanciado;
    }

    public int getTotalContratos() {
        return totalContratos;
    }

    public long getContratosAtivos() {
        return contratosAtivos;
    }
}
