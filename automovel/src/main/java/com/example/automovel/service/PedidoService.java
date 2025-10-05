package com.example.automovel.service;

import com.example.automovel.model.*;
import com.example.automovel.model.enums.StatusPedido;
import com.example.automovel.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BancoService bancoService;

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido criar(Pedido pedido) {
        pedido.setStatus(StatusPedido.PENDENTE);
        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> atualizar(Long id, Pedido pedidoAtualizado) {
        return pedidoRepository.findById(id).map(pedidoExistente -> {
            pedidoExistente.setCliente(pedidoAtualizado.getCliente());
            pedidoExistente.setAutomovel(pedidoAtualizado.getAutomovel());
            pedidoExistente.setDataInicio(pedidoAtualizado.getDataInicio());
            pedidoExistente.setDataFim(pedidoAtualizado.getDataFim());
            pedidoExistente.setValorTotal(pedidoAtualizado.getValorTotal());
            return pedidoRepository.save(pedidoExistente);
        });
    }

    public boolean deletar(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Pedido criarPedido(Cliente cliente, Automovel automovel, LocalDateTime dataInicio, 
                             LocalDateTime dataFim, Double valorDiaria) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setAutomovel(automovel);
        pedido.setDataInicio(dataInicio);
        pedido.setDataFim(dataFim);
        pedido.setValorDiaria(valorDiaria);
        pedido.setStatus(StatusPedido.PENDENTE);
        
        long dias = java.time.Duration.between(dataInicio, dataFim).toDays();
        pedido.setValorTotal(dias * valorDiaria);
        
        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> modificarPedido(Long id, LocalDateTime novaDataInicio, LocalDateTime novaDataFim) {
        return pedidoRepository.findById(id).map(pedido -> {
            if (pedido.podeSerModificado()) {
                pedido.modificarDatas(novaDataInicio, novaDataFim);
                return pedidoRepository.save(pedido);
            }
            throw new IllegalStateException("Não é possível modificar um pedido com status: " + pedido.getStatus());
        });
    }

    public Optional<Pedido> cancelarPedido(Long id) {
        return pedidoRepository.findById(id).map(pedido -> {
            if (pedido.podeSerCancelado()) {
                pedido.cancelar();
                return pedidoRepository.save(pedido);
            }
            throw new IllegalStateException("Não é possível cancelar um pedido com status: " + pedido.getStatus());
        });
    }

    public List<Pedido> buscarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> buscarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    public Optional<Pedido> avaliarPedidoFinanceiramente(Long pedidoId, Long bancoId, String parecer, boolean aprovado) {
        return pedidoRepository.findById(pedidoId).map(pedido -> {
            return bancoService.buscarPorId(bancoId).map(banco -> {
                pedido.avaliarFinanceiramente(banco, parecer, aprovado);
                return pedidoRepository.save(pedido);
            }).orElse(null);
        });
    }

    public List<Pedido> listarPedidosParaAvaliacao() {
        return pedidoRepository.findByStatus(StatusPedido.EM_ANALISE);
    }

    public List<Pedido> listarPedidosAprovados() {
        return pedidoRepository.findByStatus(StatusPedido.APROVADO);
    }

    public List<Pedido> listarPedidosPendentes() {
        return pedidoRepository.findByStatus(StatusPedido.PENDENTE);
    }
}