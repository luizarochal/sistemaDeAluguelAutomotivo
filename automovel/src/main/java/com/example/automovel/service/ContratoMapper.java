package com.example.automovel.service;

import com.example.automovel.dto.*;
import com.example.automovel.model.*;
import org.springframework.stereotype.Component;

@Component
public class ContratoMapper {

    public ContratoDTO toDTO(Contrato contrato) {
        if (contrato == null) {
            return null;
        }

        if (contrato instanceof ContratoCredito) {
            return toCreditoDTO((ContratoCredito) contrato);
        }

        ContratoDTO dto = new ContratoDTO();
        mapBaseContrato(contrato, dto);
        return dto;
    }

    public ContratoCreditoDTO toCreditoDTO(ContratoCredito contratoCredito) {
        if (contratoCredito == null) {
            return null;
        }

        ContratoCreditoDTO dto = new ContratoCreditoDTO();
        mapBaseContrato(contratoCredito, dto);
        
        dto.setBanco(toBancoDTO(contratoCredito.getBanco()));
        dto.setValorFinanciado(contratoCredito.getValorFinanciado());
        dto.setNumeroParcelas(contratoCredito.getNumeroParcelas());
        dto.setTaxaJuros(contratoCredito.getTaxaJuros());
        dto.setValorParcela(contratoCredito.getValorParcela());
        dto.setDataPrimeiroVencimento(contratoCredito.getDataPrimeiroVencimento());
        dto.setStatusCredito(contratoCredito.getStatusCredito());
        
        return dto;
    }

    private void mapBaseContrato(Contrato contrato, ContratoDTO dto) {
        dto.setId(contrato.getId());
        dto.setPedido(toPedidoResumoDTO(contrato.getPedido()));
        dto.setCliente(toClienteDTO(contrato.getCliente()));
        dto.setAutomovel(toAutomovelDTO(contrato.getAutomovel()));
        dto.setDataInicio(contrato.getDataInicio());
        dto.setDataFim(contrato.getDataFim());
        dto.setValorTotal(contrato.getValorTotal());
        dto.setValorEntrada(contrato.getValorEntrada());
        dto.setFormaPagamento(contrato.getFormaPagamento());
        dto.setDiaVencimento(contrato.getDiaVencimento());
        dto.setStatus(contrato.getStatus());
        dto.setDataCriacao(contrato.getDataCriacao());
    }

    private PedidoResumoDTO toPedidoResumoDTO(Pedido pedido) {
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

    public ContratoResumoDTO toResumoDTO(Contrato contrato) {
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