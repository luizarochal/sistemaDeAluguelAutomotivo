package com.example.automovel.service;

import com.example.automovel.dto.PedidoDTO;
import com.example.automovel.model.Cliente;
import com.example.automovel.model.Pedido;
import com.example.automovel.model.PedidoStatus;
import com.example.automovel.repository.ClienteRepository;
import com.example.automovel.repository.PedidoRepository;
import com.example.automovel.repository.AutomovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired(required = false)
    private AutomovelRepository automovelRepository;

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> listarPorClienteId(Long clienteId) {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        return cliente.map(pedidoRepository::findByCliente).orElse(List.of());
    }

    public Pedido criar(PedidoDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        Pedido pedido = new Pedido();
        // validação básica: se existir repositório de automóvel, confirme matrícula
        if (automovelRepository != null && !automovelRepository.existsByMatricula(dto.getMatricula())) {
            throw new IllegalArgumentException("Automóvel (matrícula) não encontrado: " + dto.getMatricula());
        }
        pedido.setMatricula(dto.getMatricula());
        pedido.setCliente(cliente);
        pedido.setValorEstimado(dto.getValorEstimado());
        pedido.setObservacoes(dto.getObservacoes());

        return pedidoRepository.save(pedido);
    }

    public Optional<Pedido> atualizar(Long id, PedidoDTO dto) {
        return pedidoRepository.findById(id).map(existing -> {
            if (existing.getStatus() != PedidoStatus.PENDENTE) {
                throw new IllegalStateException("Só é possível alterar pedidos pendentes");
            }
            if (automovelRepository != null && !automovelRepository.existsByMatricula(dto.getMatricula())) {
                throw new IllegalArgumentException("Automóvel (matrícula) não encontrado: " + dto.getMatricula());
            }
            existing.setMatricula(dto.getMatricula());
            existing.setValorEstimado(dto.getValorEstimado());
            existing.setObservacoes(dto.getObservacoes());
            return pedidoRepository.save(existing);
        });
    }

    @Transactional
    public boolean deletar(Long id) {
        return pedidoRepository.findById(id).map(p -> {
            if (p.getStatus() != PedidoStatus.PENDENTE) {
                throw new IllegalStateException("Só é possível cancelar pedidos pendentes");
            }
            // Deletar a entidade lida e forçar flush para garantir remoção imediata
            pedidoRepository.delete(p);
            pedidoRepository.flush();
            return true;
        }).orElse(false);
    }

    public Optional<Pedido> avaliar(Long id, boolean aprovado) {
        return pedidoRepository.findById(id).map(p -> {
            p.setStatus(aprovado ? PedidoStatus.APROVADO : PedidoStatus.REPROVADO);
            return pedidoRepository.save(p);
        });
    }
}
