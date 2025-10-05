package com.example.automovel.dto;

public class EstatisticasEmpresaDTO {
    private final int totalAutomoveis;
    private final int totalPedidos;
    private final long pedidosAtivos;
    private final double faturamentoTotal;

    public EstatisticasEmpresaDTO(int totalAutomoveis, int totalPedidos, long pedidosAtivos, double faturamentoTotal) {
        this.totalAutomoveis = totalAutomoveis;
        this.totalPedidos = totalPedidos;
        this.pedidosAtivos = pedidosAtivos;
        this.faturamentoTotal = faturamentoTotal;
    }

    // Getters
    public int getTotalAutomoveis() { return totalAutomoveis; }
    public int getTotalPedidos() { return totalPedidos; }
    public long getPedidosAtivos() { return pedidosAtivos; }
    public double getFaturamentoTotal() { return faturamentoTotal; }

    @Override
    public String toString() {
        return "EstatisticasEmpresaDTO{" +
                "totalAutomoveis=" + totalAutomoveis +
                ", totalPedidos=" + totalPedidos +
                ", pedidosAtivos=" + pedidosAtivos +
                ", faturamentoTotal=" + faturamentoTotal +
                '}';
    }
}