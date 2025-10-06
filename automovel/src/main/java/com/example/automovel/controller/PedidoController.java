package com.example.automovel.controller;

import com.example.automovel.dto.PedidoDTO;
import com.example.automovel.dto.AvaliacaoFinanceiraDTO;
import com.example.automovel.model.Pedido;
import com.example.automovel.model.enums.StatusPedido;
import com.example.automovel.model.Cliente;
import com.example.automovel.model.Automovel;
import com.example.automovel.service.PedidoService;
import com.example.automovel.service.ClienteService;
import com.example.automovel.service.AutomovelService;
import com.example.automovel.service.PedidoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.example.automovel.dto.PedidoDTO;
import com.example.automovel.model.Pedido;
import com.example.automovel.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "API para gerenciamento de pedidos de aluguel")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AutomovelService automovelService;

    @Autowired
    private PedidoMapper pedidoMapper;

    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista com todos os pedidos")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        List<PedidoDTO> pedidosDTO = pedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidosDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<PedidoDTO> buscarPorId(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(id);
        return pedido.map(p -> ResponseEntity.ok(pedidoMapper.toDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo pedido", description = "Cria um novo pedido de aluguel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<PedidoDTO> criarPedido(
            @Parameter(description = "ID do cliente", required = true) @RequestParam Long clienteId,
            @Parameter(description = "ID do automóvel", required = true) @RequestParam Long automovelId,
            @Parameter(description = "Data de início", required = true) @RequestParam LocalDate dataInicio,
            @Parameter(description = "Data de fim", required = true) @RequestParam LocalDate dataFim,
            @Parameter(description = "Valor da diária", required = true) @RequestParam Double valorDiaria) {
        try {
            Optional<Cliente> cliente = clienteService.buscarPorId(clienteId);
            Optional<Automovel> automovel = automovelService.buscarPorId(automovelId);
            
            if (cliente.isPresent() && automovel.isPresent()) {
                Pedido pedido = pedidoService.criarPedido(cliente.get(), automovel.get(), dataInicio, dataFim, valorDiaria);
                return ResponseEntity.ok(pedidoMapper.toDTO(pedido));
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/modificar")
    @Operation(summary = "Modificar pedido", description = "Modifica as datas de um pedido existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido modificado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Não é possível modificar o pedido")
    })
    public ResponseEntity<PedidoDTO> modificarPedido(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id,
            @Parameter(description = "Nova data de início", required = true) @RequestParam LocalDate novaDataInicio,
            @Parameter(description = "Nova data de fim", required = true) @RequestParam LocalDate novaDataFim) {
        try {
            Optional<Pedido> pedidoModificado = pedidoService.modificarPedido(id, novaDataInicio, novaDataFim);
            return pedidoModificado.map(p -> ResponseEntity.ok(pedidoMapper.toDTO(p)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pedido", description = "Cancela um pedido existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Não é possível cancelar o pedido")
    })
    public ResponseEntity<PedidoDTO> cancelarPedido(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id) {
        try {
            Optional<Pedido> pedidoCancelado = pedidoService.cancelarPedido(id);
            return pedidoCancelado.map(p -> ResponseEntity.ok(pedidoMapper.toDTO(p)))
                    .orElse(ResponseEntity.notFound().build());

        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/enviar-analise")
    @Operation(summary = "Enviar para análise", description = "Envia pedido para análise financeira")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido enviado para análise"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<PedidoDTO> enviarParaAnalise(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(id);
        if (pedido.isPresent()) {
            pedido.get().enviarParaAnalise();
            Pedido pedidoSalvo = pedidoService.salvar(pedido.get());
            return ResponseEntity.ok(pedidoMapper.toDTO(pedidoSalvo));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/avaliar-financeiramente")
    @Operation(summary = "Avaliar pedido financeiramente", description = "Avalia um pedido financeiramente com parecer do banco")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido avaliado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Não é possível avaliar o pedido")
    })
    public ResponseEntity<PedidoDTO> avaliarPedidoFinanceiramente(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id,
            @RequestBody AvaliacaoFinanceiraDTO avaliacaoDTO) {
        try {
            Optional<Pedido> pedidoAvaliado = pedidoService.avaliarPedidoFinanceiramente(
                    id, avaliacaoDTO.getBancoId(), avaliacaoDTO.getParecer(), avaliacaoDTO.isAprovado());
            return pedidoAvaliado.map(p -> ResponseEntity.ok(pedidoMapper.toDTO(p)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar pedidos por cliente", description = "Retorna pedidos de um cliente específico")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<PedidoDTO>> listarPorCliente(
            @Parameter(description = "ID do cliente", required = true) @PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.buscarPorCliente(clienteId);
        List<PedidoDTO> pedidosDTO = pedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidosDTO);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar pedidos por status", description = "Retorna pedidos com um status específico")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<PedidoDTO>> listarPorStatus(
            @Parameter(description = "Status do pedido", required = true) @PathVariable String status) {
        try {
            StatusPedido statusPedido = StatusPedido.valueOf(status.toUpperCase());
            List<Pedido> pedidos = pedidoService.buscarPorStatus(statusPedido);
            List<PedidoDTO> pedidosDTO = pedidos.stream()
                    .map(pedidoMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(pedidosDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pendentes")
    @Operation(summary = "Listar pedidos pendentes", description = "Retorna todos os pedidos com status pendente")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos pendentes retornada com sucesso")
    public ResponseEntity<List<PedidoDTO>> listarPedidosPendentes() {
        List<Pedido> pedidos = pedidoService.listarPedidosPendentes();
        List<PedidoDTO> pedidosDTO = pedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidosDTO);
    }

    @GetMapping("/aprovados")
    @Operation(summary = "Listar pedidos aprovados", description = "Retorna todos os pedidos com status aprovado")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos aprovados retornada com sucesso")
    public ResponseEntity<List<PedidoDTO>> listarPedidosAprovados() {
        List<Pedido> pedidos = pedidoService.listarPedidosAprovados();
        List<PedidoDTO> pedidosDTO = pedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidosDTO);
    }

    @GetMapping("/analise")
    @Operation(summary = "Listar pedidos em análise", description = "Retorna todos os pedidos com status em análise")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos em análise retornada com sucesso")
    public ResponseEntity<List<PedidoDTO>> listarPedidosParaAvaliacao() {
        List<Pedido> pedidos = pedidoService.listarPedidosParaAvaliacao();
        List<PedidoDTO> pedidosDTO = pedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidosDTO);
    }
}

