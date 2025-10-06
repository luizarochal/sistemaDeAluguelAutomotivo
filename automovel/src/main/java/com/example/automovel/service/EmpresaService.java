package com.example.automovel.service;

import com.example.automovel.model.Empresa;
import com.example.automovel.model.Pedido;
import com.example.automovel.model.enums.StatusPedido;
import com.example.automovel.model.enums.TipoProprietario;
import com.example.automovel.model.Automovel;
import com.example.automovel.dto.EmpresaDTO;
import com.example.automovel.dto.EstatisticasEmpresaDTO;
import com.example.automovel.repository.EmpresaRepository;
import com.example.automovel.repository.PedidoRepository;
import com.example.automovel.repository.AutomovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private AutomovelRepository automovelRepository;

    private static final int MAX_SOCIOS = 5;

    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> buscarPorId(Long id) {
        return empresaRepository.findById(id);
    }

    public Optional<Empresa> buscarPorCNPJ(String cnpj) {
        return empresaRepository.findByCNPJ(cnpj);
    }

    public Empresa criar(EmpresaDTO empresaDTO) {
        validarEmpresa(empresaDTO);
        Empresa empresa = new Empresa(
                empresaDTO.getCNPJ(),
                empresaDTO.getNome(),
                empresaDTO.getEndereco(),
                empresaDTO.getTelefone(),
                empresaDTO.getSocios());
        return empresaRepository.save(empresa);
    }

    public Optional<Empresa> atualizar(Long id, EmpresaDTO empresaDTO) {
        return empresaRepository.findById(id).map(empresaExistente -> {
            validarEmpresa(empresaDTO);
            empresaExistente.setCNPJ(empresaDTO.getCNPJ());
            empresaExistente.setNome(empresaDTO.getNome());
            empresaExistente.setEndereco(empresaDTO.getEndereco());
            empresaExistente.setTelefone(empresaDTO.getTelefone());
            empresaExistente.setSocios(empresaDTO.getSocios());
            return empresaRepository.save(empresaExistente);
        });
    }

    public boolean deletar(Long id) {
        if (empresaRepository.existsById(id)) {
            empresaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Pedido> avaliarPedidoComercialmente(Long pedidoId, Long empresaId, String parecer, boolean aprovado) {
        return pedidoRepository.findById(pedidoId).flatMap(pedido ->
            buscarPorId(empresaId).map(empresa -> {
                
                String parecerCompleto = "Avaliação Comercial - " + empresa.getNome() + ": " + parecer;
                
                if (aprovado) {
                    pedido.setStatus(StatusPedido.EM_ANALISE);
                } else {
                    pedido.setStatus(StatusPedido.REPROVADO);
                }
                
                pedido.setParecerFinanceiro(parecerCompleto);
                pedido.adicionarHistorico("Avaliação comercial: " + (aprovado ? "Aprovado" : "Reprovado"));
                
                return pedidoRepository.save(pedido);
            })
        );
    }

    public List<Automovel> listarAutomoveisEmpresa(Long empresaId) {
        return automovelRepository.findByProprietarioAndEmpresaId(TipoProprietario.EMPRESA, empresaId);
    }


    public List<Pedido> listarPedidosAutomoveisEmpresa(Long empresaId) {
        List<Automovel> automoveisEmpresa = listarAutomoveisEmpresa(empresaId);
        return pedidoRepository.findByAutomovelIn(automoveisEmpresa);
    }

    public Optional<Pedido> modificarAvaliacaoComercial(Long pedidoId, Long empresaId, String novoParecer) {
        return pedidoRepository.findById(pedidoId).flatMap(pedido ->
            buscarPorId(empresaId).map(empresa -> {
                
                pedido.setParecerFinanceiro("Avaliação Comercial Modificada - " + empresa.getNome() + ": " + novoParecer);
                pedido.adicionarHistorico("Avaliação comercial modificada");
                
                return pedidoRepository.save(pedido);
            })
        );
    }

    public EstatisticasEmpresaDTO obterEstatisticas(Long empresaId) {
        List<Automovel> automoveis = listarAutomoveisEmpresa(empresaId);
        List<Pedido> pedidos = pedidoRepository.findByAutomovelIn(automoveis);
        
        double faturamentoTotal = pedidos.stream()
            .filter(p -> p.getStatus() == StatusPedido.CONTRATADO)
            .mapToDouble(p -> p.getValorTotal() != null ? p.getValorTotal() : 0.0)
            .sum();
        
        long pedidosAtivos = pedidos.stream()
            .filter(p -> p.getStatus() == StatusPedido.CONTRATADO)
            .count();
        
        return new EstatisticasEmpresaDTO(
            automoveis.size(), 
            pedidos.size(), 
            pedidosAtivos, 
            faturamentoTotal
        );
    }

    private void validarEmpresa(EmpresaDTO empresaDTO) {
        if (empresaDTO.getSocios() != null && empresaDTO.getSocios().size() > MAX_SOCIOS) {
            throw new IllegalArgumentException("Máximo de " + MAX_SOCIOS + " sócios permitidos");
        }
        if (empresaDTO.getCNPJ() == null || empresaDTO.getCNPJ().trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ é obrigatório");
        }
        if (empresaDTO.getNome() == null || empresaDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

    }
}