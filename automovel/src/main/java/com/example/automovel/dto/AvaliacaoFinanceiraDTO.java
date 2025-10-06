package com.example.automovel.dto;

public class AvaliacaoFinanceiraDTO {
    private Long bancoId;
    private String parecer;
    private boolean aprovado;

   
    public AvaliacaoFinanceiraDTO() {}

    public AvaliacaoFinanceiraDTO(Long bancoId, String parecer, boolean aprovado) {
        this.bancoId = bancoId;
        this.parecer = parecer;
        this.aprovado = aprovado;
    }

    // Getters e Setters
    public Long getBancoId() { return bancoId; }
    public void setBancoId(Long bancoId) { this.bancoId = bancoId; }

    public String getParecer() { return parecer; }
    public void setParecer(String parecer) { this.parecer = parecer; }

    public boolean isAprovado() { return aprovado; }
    public void setAprovado(boolean aprovado) { this.aprovado = aprovado; }

    @Override
    public String toString() {
        return "AvaliacaoFinanceiraDTO{" +
                "bancoId=" + bancoId +
                ", parecer='" + parecer + '\'' +
                ", aprovado=" + aprovado +
                '}';
    }
}