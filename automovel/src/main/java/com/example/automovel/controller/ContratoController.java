package com.example.automovel.controller;

import com.example.automovel.dto.*;
import com.example.automovel.model.Contrato;
import com.example.automovel.model.ContratoCredito;
import com.example.automovel.model.enums.StatusContratoCredito;
import com.example.automovel.service.ContratoService;
import com.example.automovel.service.ContratoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contratos")
@Tag(name = "Contratos", description = "API para gerenciamento de contratos de aluguel")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private ContratoMapper contratoMapper;

    @PostMapping("/normal/{pedidoId}")
    @Operation(summary = "Criar contrato normal", description = "Cria um contrato normal a partir de um pedido aprovado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contrato criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Não é possível criar contrato")
    })
    public ResponseEntity<ContratoDTO> criarContratoNormal(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long pedidoId,
            @Parameter(description = "Valor da entrada", required = true) @RequestParam Double valorEntrada,
            @Parameter(description = "Forma de pagamento", required = true) @RequestParam String formaPagamento,
            @Parameter(description = "Dia de vencimento", required = false) @RequestParam(required = false) Integer diaVencimento) {
        try {
            Optional<Contrato> contrato = contratoService.criarContratoNormal(pedidoId, valorEntrada, formaPagamento,
                    diaVencimento);
            return contrato.map(c -> ResponseEntity.ok(contratoMapper.toDTO(c)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/credito/{pedidoId}")
    @Operation(summary = "Criar contrato com crédito", description = "Cria um contrato com financiamento bancário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contrato de crédito criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido ou banco não encontrado"),
            @ApiResponse(responseCode = "400", description = "Não é possível criar contrato de crédito")
    })
    public ResponseEntity<ContratoCreditoDTO> criarContratoCredito(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long pedidoId,
            @Parameter(description = "ID do banco", required = true) @RequestParam Long bancoId,
            @Parameter(description = "Valor da entrada", required = true) @RequestParam Double valorEntrada,
            @Parameter(description = "Forma de pagamento", required = true) @RequestParam String formaPagamento,
            @Parameter(description = "Dia de vencimento", required = false) @RequestParam(required = false) Integer diaVencimento,
            @Parameter(description = "Valor financiado", required = true) @RequestParam Double valorFinanciado,
            @Parameter(description = "Número de parcelas", required = true) @RequestParam Integer numeroParcelas,
            @Parameter(description = "Taxa de juros", required = true) @RequestParam Double taxaJuros,
            @Parameter(description = "Data primeiro vencimento", required = true) @RequestParam LocalDate dataPrimeiroVencimento) {
        try {
            Optional<ContratoCredito> contrato = contratoService.criarContratoCredito(
                    pedidoId, bancoId, valorEntrada, formaPagamento, diaVencimento,
                    valorFinanciado, numeroParcelas, taxaJuros, dataPrimeiroVencimento);
            return contrato.map(c -> ResponseEntity.ok(contratoMapper.toCreditoDTO(c)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos os contratos", description = "Retorna uma lista com todos os contratos")
    @ApiResponse(responseCode = "200", description = "Lista de contratos retornada com sucesso")
    public ResponseEntity<List<ContratoDTO>> listarTodos() {
        List<Contrato> contratos = contratoService.listarTodosContratos();
        List<ContratoDTO> contratosDTO = contratos.stream()
                .map(contratoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contratosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar contrato por ID", description = "Retorna um contrato específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contrato encontrado"),
            @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    })
    public ResponseEntity<ContratoDTO> buscarPorId(
            @Parameter(description = "ID do contrato", required = true) @PathVariable Long id) {
        Optional<Contrato> contrato = contratoService.buscarContratoPorId(id);
        return contrato.map(c -> ResponseEntity.ok(contratoMapper.toDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar contratos por cliente", description = "Retorna contratos de um cliente específico")
    @ApiResponse(responseCode = "200", description = "Lista de contratos retornada com sucesso")
    public ResponseEntity<List<ContratoDTO>> listarPorCliente(
            @Parameter(description = "ID do cliente", required = true) @PathVariable Long clienteId) {
        List<Contrato> contratos = contratoService.listarContratosPorCliente(clienteId);
        List<ContratoDTO> contratosDTO = contratos.stream()
                .map(contratoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contratosDTO);
    }

    @GetMapping("/credito")
    @Operation(summary = "Listar contratos de crédito", description = "Retorna todos os contratos de crédito")
    @ApiResponse(responseCode = "200", description = "Lista de contratos de crédito retornada com sucesso")
    public ResponseEntity<List<ContratoCreditoDTO>> listarContratosCredito() {
        List<ContratoCredito> contratos = contratoService.listarTodosContratosCredito();
        List<ContratoCreditoDTO> contratosDTO = contratos.stream()
                .map(contratoMapper::toCreditoDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contratosDTO);
    }

    @GetMapping("/credito/banco/{bancoId}")
    @Operation(summary = "Listar contratos de crédito por banco", description = "Retorna contratos de crédito de um banco específico")
    @ApiResponse(responseCode = "200", description = "Lista de contratos de crédito retornada com sucesso")
    public ResponseEntity<List<ContratoCreditoDTO>> listarContratosCreditoPorBanco(
            @Parameter(description = "ID do banco", required = true) @PathVariable Long bancoId) {
        List<ContratoCredito> contratos = contratoService.listarContratosCreditoPorBanco(bancoId);
        List<ContratoCreditoDTO> contratosDTO = contratos.stream()
                .map(contratoMapper::toCreditoDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contratosDTO);
    }

    @PutMapping("/{id}/finalizar")
    @Operation(summary = "Finalizar contrato", description = "Marca um contrato como finalizado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contrato finalizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    })
    public ResponseEntity<ContratoDTO> finalizarContrato(
            @Parameter(description = "ID do contrato", required = true) @PathVariable Long id) {
        Optional<Contrato> contrato = contratoService.finalizarContrato(id);
        return contrato.map(c -> ResponseEntity.ok(contratoMapper.toDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/credito/{id}/status")
    @Operation(summary = "Atualizar status do contrato de crédito", description = "Atualiza o status de um contrato de crédito")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    })
    public ResponseEntity<ContratoCreditoDTO> atualizarStatusContratoCredito(
            @Parameter(description = "ID do contrato", required = true) @PathVariable Long id,
            @Parameter(description = "Novo status", required = true) @RequestParam StatusContratoCredito novoStatus) {
        Optional<ContratoCredito> contrato = contratoService.atualizarStatusContratoCredito(id, novoStatus);
        return contrato.map(c -> ResponseEntity.ok(contratoMapper.toCreditoDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estatisticas")
    @Operation(summary = "Obter estatísticas dos contratos", description = "Retorna estatísticas gerais dos contratos")
    @ApiResponse(responseCode = "200", description = "Estatísticas retornadas com sucesso")
    public ResponseEntity<EstatisticasContratosDTO> obterEstatisticas() {
        EstatisticasContratosDTO estatisticas = contratoService.obterEstatisticasContratos();
        return ResponseEntity.ok(estatisticas);
    }
}