package com.example.automovel.model.enums;

public enum StatusPedido {
    PENDENTE("Aguardando envio para análise"),
    EM_ANALISE("Em análise financeira"),
    APROVADO("Aprovado - Aguardando contratação"),
    REPROVADO("Reprovado financeiramente"),
    CANCELADO("Cancelado pelo cliente"),
    CONTRATADO("Contrato gerado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}