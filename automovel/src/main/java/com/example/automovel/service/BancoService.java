package com.example.automovel.service;

import com.example.automovel.model.Banco;
import com.example.automovel.model.Pedido;
import com.example.automovel.model.enums.StatusContratoCredito;
import com.example.automovel.model.enums.StatusPedido;
import com.example.automovel.model.ContratoCredito;
import com.example.automovel.dto.BancoDTO;
import com.example.automovel.dto.AvaliacaoFinanceiraDTO;
import com.example.automovel.dto.EstatisticasBancoDTO;
import com.example.automovel.repository.BancoRepository;
import com.example.automovel.repository.PedidoRepository;
import com.example.automovel.repository.ContratoCreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ContratoCreditoRepository contratoCreditoRepository;

    public List<Banco> listarTodos() {
        return bancoRepository.findAll();
    }

    public Optional<Banco> buscarPorId(Long id) {
        return bancoRepository.findById(id);
    }

    public Optional<Banco> buscarPorCodigo(String codigo) {
        return bancoRepository.findByCodigo(codigo);
    }

    public Banco criar(BancoDTO bancoDTO) {
        validarBanco(bancoDTO);
        Banco banco = new Banco(
                bancoDTO.getCodigo(),
                bancoDTO.getNome(),
                bancoDTO.getCentralTelefone());
        return bancoRepository.save(banco);
    }

    public Optional<Banco> atualizar(Long id, BancoDTO bancoDTO) {
        return bancoRepository.findById(id).map(bancoExistente -> {
            validarBanco(bancoDTO);
            bancoExistente.setCodigo(bancoDTO.getCodigo());
            bancoExistente.setNome(bancoDTO.getNome());
            bancoExistente.setCentralTelefone(bancoDTO.getCentralTelefone());
            return bancoRepository.save(bancoExistente);
        });
    }

    public boolean deletar(Long id) {
        if (bancoRepository.existsById(id)) {
            bancoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Pedido> avaliarPedido(Long pedidoId, AvaliacaoFinanceiraDTO avaliacaoDTO) {
        return pedidoRepository.findById(pedidoId).flatMap(pedido -> 
            buscarPorId(avaliacaoDTO.getBancoId()).map(banco -> {
                
                pedido.avaliarFinanceiramente(banco, avaliacaoDTO.getParecer(), avaliacaoDTO.isAprovado());
                
                return pedidoRepository.save(pedido);
            })
        );
    }

   
    public List<Pedido> listarPedidosParaAvaliacao() {
        return pedidoRepository.findByStatus(StatusPedido.EM_ANALISE);
    }

   
    public List<Pedido> listarPedidosAvaliados(Long bancoId) {
        return pedidoRepository.findByBancoAvaliadorIdAndStatusIn(
            bancoId, 
            List.of(StatusPedido.APROVADO, StatusPedido.REPROVADO)
        );
    }

   
    public List<ContratoCredito> listarContratosCredito(Long bancoId) {
        return contratoCreditoRepository.findByBancoId(bancoId);
    }

  
    public EstatisticasBancoDTO obterEstatisticas(Long bancoId) {
        List<ContratoCredito> contratos = contratoCreditoRepository.findByBancoId(bancoId);
        
        double totalFinanciado = contratos.stream()
            .mapToDouble(ContratoCredito::getValorFinanciado)
            .sum();
        
        long contratosAtivos = contratos.stream()
            .filter(c -> c.getStatusCredito() == StatusContratoCredito.ATIVO)
            .count();
        
        return new EstatisticasBancoDTO(totalFinanciado, contratos.size(), contratosAtivos);
    }

    
    public Optional<Pedido> modificarAvaliacaoPedido(Long pedidoId, Long bancoId, String novoParecer) {
        return pedidoRepository.findById(pedidoId).flatMap(pedido -> 
            buscarPorId(bancoId).map(banco -> {
                if (pedido.getBancoAvaliador() != null && 
                    pedido.getBancoAvaliador().getId().equals(bancoId)) {
                    
                    pedido.setParecerFinanceiro(novoParecer);
                    pedido.setDataModificacao(LocalDateTime.now());
                    pedido.adicionarHistorico("Avaliação modificada: " + novoParecer);
                    
                    return pedidoRepository.save(pedido);
                }
                return pedido;
            })
        );
    }

    private void validarBanco(BancoDTO bancoDTO) {
        if (bancoDTO.getCodigo() == null || bancoDTO.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("Código do banco é obrigatório");
        }
        if (bancoDTO.getNome() == null || bancoDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do banco é obrigatório");
        }
    }
}