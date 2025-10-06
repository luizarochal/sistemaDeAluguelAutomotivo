package com.example.automovel.service;

import com.example.automovel.dto.EstatisticasContratosDTO;
import com.example.automovel.model.*;
import com.example.automovel.model.enums.StatusContrato;
import com.example.automovel.model.enums.StatusContratoCredito;
import com.example.automovel.model.enums.StatusPedido;
import com.example.automovel.repository.ContratoRepository;
import com.example.automovel.repository.ContratoCreditoRepository;
import com.example.automovel.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ContratoCreditoRepository contratoCreditoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BancoService bancoService;

    // Métodos para Contrato Normal
    public List<Contrato> listarTodosContratos() {
        return contratoRepository.findAll();
    }

    public Optional<Contrato> buscarContratoPorId(Long id) {
        return contratoRepository.findById(id);
    }

    public List<Contrato> listarContratosPorCliente(Long clienteId) {
        return contratoRepository.findByClienteId(clienteId);
    }

    public List<Contrato> listarContratosNormais() {
        return contratoRepository.findContratosNormais();
    }

    public Optional<Contrato> criarContratoNormal(Long pedidoId, Double valorEntrada, 
                                                 String formaPagamento, Integer diaVencimento) {
        return pedidoRepository.findById(pedidoId).map(pedido -> {
            if (pedido.getStatus() == StatusPedido.APROVADO) {
                Contrato contrato = new Contrato(
                    pedido,
                    pedido.getCliente(),
                    pedido.getAutomovel(),
                    pedido.getDataInicio(),
                    pedido.getDataFim(),
                    pedido.getValorTotal(),
                    valorEntrada,
                    formaPagamento,
                    diaVencimento
                );
                
                pedido.setStatus(StatusPedido.CONTRATADO);
                pedidoRepository.save(pedido);
                
                return contratoRepository.save(contrato);
            }
            throw new IllegalStateException("Não é possível criar contrato para pedido com status: " + pedido.getStatus());
        });
    }

    public Optional<Contrato> atualizarContrato(Long id, Contrato contratoAtualizado) {
        return contratoRepository.findById(id).map(contratoExistente -> {
            contratoExistente.setDataInicio(contratoAtualizado.getDataInicio());
            contratoExistente.setDataFim(contratoAtualizado.getDataFim());
            contratoExistente.setValorTotal(contratoAtualizado.getValorTotal());
            contratoExistente.setValorEntrada(contratoAtualizado.getValorEntrada());
            contratoExistente.setFormaPagamento(contratoAtualizado.getFormaPagamento());
            contratoExistente.setDiaVencimento(contratoAtualizado.getDiaVencimento());
            return contratoRepository.save(contratoExistente);
        });
    }

    public boolean deletarContrato(Long id) {
        if (contratoRepository.existsById(id)) {
            contratoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Métodos para ContratoCredito
    public List<ContratoCredito> listarTodosContratosCredito() {
        return contratoCreditoRepository.findAll();
    }

    public Optional<ContratoCredito> buscarContratoCreditoPorId(Long id) {
        return contratoCreditoRepository.findById(id);
    }

    public List<ContratoCredito> listarContratosCreditoPorBanco(Long bancoId) {
        return contratoCreditoRepository.findByBancoId(bancoId);
    }

    public Optional<ContratoCredito> criarContratoCredito(Long pedidoId, Long bancoId, 
                                                         Double valorEntrada, String formaPagamento, 
                                                         Integer diaVencimento, Double valorFinanciado, 
                                                         Integer numeroParcelas, Double taxaJuros, 
                                                         LocalDate dataPrimeiroVencimento) {
        return pedidoRepository.findById(pedidoId).map(pedido -> {
            if (pedido.getStatus() == StatusPedido.APROVADO) {
                return bancoService.buscarPorId(bancoId).map(banco -> {
                    ContratoCredito contratoCredito = new ContratoCredito(
                        pedido,
                        pedido.getCliente(),
                        pedido.getAutomovel(),
                        pedido.getDataInicio(),
                        pedido.getDataFim(),
                        pedido.getValorTotal(),
                        valorEntrada,
                        formaPagamento,
                        diaVencimento,
                        banco,
                        valorFinanciado,
                        numeroParcelas,
                        taxaJuros,
                        dataPrimeiroVencimento
                    );
                    
                    pedido.setStatus(StatusPedido.CONTRATADO);
                    pedidoRepository.save(pedido);
                    
                    return contratoCreditoRepository.save(contratoCredito);
                }).orElseThrow(() -> new IllegalArgumentException("Banco não encontrado com ID: " + bancoId));
            }
            throw new IllegalStateException("Não é possível criar contrato de crédito para pedido com status: " + pedido.getStatus());
        });
    }

    public Optional<ContratoCredito> atualizarContratoCredito(Long id, ContratoCredito contratoAtualizado) {
        return contratoCreditoRepository.findById(id).map(contratoExistente -> {
            contratoExistente.setValorFinanciado(contratoAtualizado.getValorFinanciado());
            contratoExistente.setNumeroParcelas(contratoAtualizado.getNumeroParcelas());
            contratoExistente.setTaxaJuros(contratoAtualizado.getTaxaJuros());
            contratoExistente.setDataPrimeiroVencimento(contratoAtualizado.getDataPrimeiroVencimento());
            return contratoCreditoRepository.save(contratoExistente);
        });
    }

    public Optional<ContratoCredito> atualizarStatusContratoCredito(Long id, StatusContratoCredito novoStatus) {
        return contratoCreditoRepository.findById(id).map(contrato -> {
            contrato.setStatusCredito(novoStatus);
            return contratoCreditoRepository.save(contrato);
        });
    }

    // Métodos gerais de contrato
    public Optional<Contrato> finalizarContrato(Long id) {
        return contratoRepository.findById(id).map(contrato -> {
            contrato.setStatus(StatusContrato.FINALIZADO);
            return contratoRepository.save(contrato);
        });
    }

    public Optional<Contrato> cancelarContrato(Long id) {
        return contratoRepository.findById(id).map(contrato -> {
            contrato.setStatus(StatusContrato.CANCELADO);
            return contratoRepository.save(contrato);
        });
    }

    public List<Contrato> listarContratosPorStatus(StatusContrato status) {
        return contratoRepository.findByStatus(status);
    }

    // Estatísticas
    public EstatisticasContratosDTO obterEstatisticasContratos() {
        List<Contrato> todosContratos = contratoRepository.findAll();
        List<ContratoCredito> contratosCredito = contratoCreditoRepository.findAll();
        
        double valorTotalContratos = todosContratos.stream()
            .mapToDouble(Contrato::getValorTotal)
            .sum();
        
        long contratosAtivos = todosContratos.stream()
            .filter(c -> c.getStatus() == StatusContrato.ATIVO)
            .count();
        
        double valorTotalFinanciado = contratosCredito.stream()
            .mapToDouble(ContratoCredito::getValorFinanciado)
            .sum();
        
        return new EstatisticasContratosDTO(
            todosContratos.size(),
            contratosCredito.size(),
            contratosAtivos,
            valorTotalContratos,
            valorTotalFinanciado
        );
    }

}