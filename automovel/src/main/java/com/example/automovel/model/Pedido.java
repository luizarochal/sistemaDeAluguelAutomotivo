package com.example.automovel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataSolicitacao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private PedidoStatus status = PedidoStatus.PENDENTE;

    // Referência ao automóvel pelo seu número de matrícula (conforme enunciado)
    @Column(name = "automovel_matricula", length = 100)
    private String matricula;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Se houver contrato de crédito associado
    private Long contratoCreditoId;

    private Double valorEstimado;

    @Column(length = 1000)
    private String observacoes;

    public Pedido() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public PedidoStatus getStatus() {
        return status;
    }

    public void setStatus(PedidoStatus status) {
        this.status = status;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getContratoCreditoId() {
        return contratoCreditoId;
    }

    public void setContratoCreditoId(Long contratoCreditoId) {
        this.contratoCreditoId = contratoCreditoId;
    }

    public Double getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(Double valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
