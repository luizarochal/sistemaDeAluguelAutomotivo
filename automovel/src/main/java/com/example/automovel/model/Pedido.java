package com.example.automovel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.automovel.model.enums.StatusPedido;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "automovel_id", nullable = false)
    private Automovel automovel;

    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @Column(nullable = false)
    private LocalDateTime dataInicio;

    @Column(nullable = false)
    private LocalDateTime dataFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @Column(precision = 10, scale = 2)
    private Double valorDiaria;

    @Column(precision = 10, scale = 2)
    private Double valorTotal;

    @Column(length = 1000)
    private String parecerFinanceiro;

    @ManyToOne
    @JoinColumn(name = "banco_avaliador_id")
    private Banco bancoAvaliador;

    @Column
    private LocalDateTime dataAvaliacao;

    @Column
    private LocalDateTime dataModificacao;

    @ElementCollection
    @CollectionTable(name = "pedido_historico", joinColumns = @JoinColumn(name = "pedido_id"))
    private List<String> historicoAlteracoes;

    @OneToOne(mappedBy = "pedido")
    private Contrato contrato;

    public Pedido() {
        this.dataPedido = LocalDateTime.now();
        this.status = StatusPedido.PENDENTE;
        this.historicoAlteracoes = new ArrayList<>();
        adicionarHistorico("Pedido criado em " + this.dataPedido);
    }

    public Pedido(Cliente cliente, Automovel automovel, LocalDateTime dataInicio, LocalDateTime dataFim,
            Double valorDiaria) {
        this();
        this.cliente = cliente;
        this.automovel = automovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorDiaria = valorDiaria;
        calcularValorTotal();
    }

    private void calcularValorTotal() {
        if (dataInicio != null && dataFim != null && valorDiaria != null) {
            long dias = java.time.Duration.between(dataInicio, dataFim).toDays();
            this.valorTotal = dias * valorDiaria;
        }
    }

    public void adicionarHistorico(String mensagem) {
        if (this.historicoAlteracoes == null) {
            this.historicoAlteracoes = new ArrayList<>();
        }
        this.historicoAlteracoes.add(LocalDateTime.now() + " - " + mensagem);
    }

    public void modificarDatas(LocalDateTime novaDataInicio, LocalDateTime novaDataFim) {
        if (this.status == StatusPedido.PENDENTE || this.status == StatusPedido.EM_ANALISE) {
            String historico = String.format("Datas modificadas: %s até %s → %s até %s",
                    this.dataInicio, this.dataFim, novaDataInicio, novaDataFim);

            this.dataInicio = novaDataInicio;
            this.dataFim = novaDataFim;
            this.dataModificacao = LocalDateTime.now();
            calcularValorTotal();

            adicionarHistorico(historico);
        } else {
            throw new IllegalStateException("Não é possível modificar um pedido com status: " + this.status);
        }
    }

    public void cancelar() {
        if (this.status != StatusPedido.CONTRATADO && this.status != StatusPedido.CANCELADO) {
            this.status = StatusPedido.CANCELADO;
            this.dataModificacao = LocalDateTime.now();
            adicionarHistorico("Pedido cancelado");
        } else {
            throw new IllegalStateException("Não é possível cancelar um pedido com status: " + this.status);
        }
    }

    public void enviarParaAnalise() {
        if (this.status == StatusPedido.PENDENTE) {
            this.status = StatusPedido.EM_ANALISE;
            this.dataModificacao = LocalDateTime.now();
            adicionarHistorico("Pedido enviado para análise financeira");
        }
    }

    public void avaliarFinanceiramente(Banco banco, String parecer, boolean aprovado) {
        this.bancoAvaliador = banco;
        this.parecerFinanceiro = parecer;
        this.dataAvaliacao = LocalDateTime.now();
        this.status = aprovado ? StatusPedido.APROVADO : StatusPedido.REPROVADO;
        this.dataModificacao = LocalDateTime.now();

        String acao = aprovado ? "aprovado" : "reprovado";
        adicionarHistorico("Avaliação financeira: " + acao + " por " + banco.getNome() + " - " + parecer);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        adicionarHistorico("Cliente definido: " + cliente.getNome());
    }

    public Automovel getAutomovel() {
        return automovel;
    }

    public void setAutomovel(Automovel automovel) {
        this.automovel = automovel;
        adicionarHistorico("Automóvel definido: " + automovel.getMarca() + " " + automovel.getModelo());
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
        calcularValorTotal();
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
        calcularValorTotal();
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        StatusPedido statusAnterior = this.status;
        this.status = status;
        this.dataModificacao = LocalDateTime.now();
        adicionarHistorico("Status alterado: " + statusAnterior + " → " + status);
    }

    public Double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(Double valorDiaria) {
        this.valorDiaria = valorDiaria;
        calcularValorTotal();
        adicionarHistorico("Valor diária alterado para: R$ " + valorDiaria);
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getParecerFinanceiro() {
        return parecerFinanceiro;
    }

    public void setParecerFinanceiro(String parecerFinanceiro) {
        this.parecerFinanceiro = parecerFinanceiro;
    }

    public Banco getBancoAvaliador() {
        return bancoAvaliador;
    }

    public void setBancoAvaliador(Banco bancoAvaliador) {
        this.bancoAvaliador = bancoAvaliador;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public List<String> getHistoricoAlteracoes() {
        return historicoAlteracoes;
    }

    public void setHistoricoAlteracoes(List<String> historicoAlteracoes) {
        this.historicoAlteracoes = historicoAlteracoes;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public boolean podeSerModificado() {
        return this.status == StatusPedido.PENDENTE || this.status == StatusPedido.EM_ANALISE;
    }

    public boolean podeSerCancelado() {
        return this.status != StatusPedido.CONTRATADO && this.status != StatusPedido.CANCELADO;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + (cliente != null ? cliente.getNome() : "null") +
                ", automovel=" + (automovel != null ? automovel.getMarca() + " " + automovel.getModelo() : "null") +
                ", status=" + status +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
