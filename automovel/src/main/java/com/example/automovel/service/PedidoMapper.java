package com.example.automovel.service;

import com.example.automovel.dto.*;
import com.example.automovel.model.*;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setCliente(toClienteDTO(pedido.getCliente()));
        dto.setAutomovel(toAutomovelDTO(pedido.getAutomovel()));
        dto.setDataPedido(pedido.getDataPedido());
        dto.setDataInicio(pedido.getDataInicio());
        dto.setDataFim(pedido.getDataFim());
        dto.setStatus(pedido.getStatus());
        dto.setValorDiaria(pedido.getValorDiaria());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setParecerFinanceiro(pedido.getParecerFinanceiro());
        dto.setBancoAvaliador(toBancoDTO(pedido.getBancoAvaliador()));
        dto.setDataAvaliacao(pedido.getDataAvaliacao());
        dto.setDataModificacao(pedido.getDataModificacao());
        dto.setHistoricoAlteracoes(pedido.getHistoricoAlteracoes());
        dto.setContrato(toContratoResumoDTO(pedido.getContrato()));
        
        return dto;
    }

    public PedidoResumoDTO toResumoDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        return new PedidoResumoDTO(
            pedido.getId(),
            pedido.getDataPedido(),
            pedido.getDataInicio(),
            pedido.getDataFim(),
            pedido.getStatus(),
            pedido.getValorTotal()
        );
    }

    private ClienteDTO toClienteDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteDTO(
            cliente.getRG(),
            cliente.getCPF(),
            cliente.getNome(),
            cliente.getEndereco(),
            cliente.getProfissao(),
            cliente.getEntidadesEmpregadoras()
        );
    }

    private AutomovelDTO toAutomovelDTO(Automovel automovel) {
        if (automovel == null) {
            return null;
        }

        return new AutomovelDTO(
            automovel.getMatricula(),
            automovel.getAno(),
            automovel.getMarca(),
            automovel.getModelo(),
            automovel.getPlaca(),
            automovel.getProprietario(),
            automovel.getDisponivel(),
            automovel.getEmpresaProprietaria() != null ? automovel.getEmpresaProprietaria().getId() : null,
            automovel.getBancoProprietario() != null ? automovel.getBancoProprietario().getId() : null
        );
    }

    private BancoDTO toBancoDTO(Banco banco) {
        if (banco == null) {
            return null;
        }

        return new BancoDTO(
            banco.getCodigo(),
            banco.getNome(),
            banco.getCentralTelefone()
        );
    }

    private ContratoResumoDTO toContratoResumoDTO(Contrato contrato) {
        if (contrato == null) {
            return null;
        }

        return new ContratoResumoDTO(
            contrato.getId(),
            contrato.getDataInicio(),
            contrato.getDataFim(),
            contrato.getValorTotal(),
            contrato.getStatus()
        );
    }
}