package com.example.automovel.controller;

import com.example.automovel.model.Pedido;
import com.example.automovel.model.enums.StatusPedido;
import com.example.automovel.model.Cliente;
import com.example.automovel.model.Automovel;
import com.example.automovel.service.PedidoService;
import com.example.automovel.service.ClienteService;
import com.example.automovel.service.AutomovelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista com todos os pedidos")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<Pedido>> listarTodos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Pedido> buscarPorId(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(id);
        return pedido.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo pedido", description = "Cria um novo pedido de aluguel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Pedido> criarPedido(
            @Parameter(description = "ID do cliente", required = true) @RequestParam Long clienteId,
            @Parameter(description = "ID do automóvel", required = true) @RequestParam Long automovelId,
            @Parameter(description = "Data de início", required = true) @RequestParam LocalDateTime dataInicio,
            @Parameter(description = "Data de fim", required = true) @RequestParam LocalDateTime dataFim,
            @Parameter(description = "Valor da diária", required = true) @RequestParam Double valorDiaria) {
        try {
            Optional<Cliente> cliente = clienteService.buscarPorId(clienteId);
            Optional<Automovel> automovel = automovelService.buscarPorId(automovelId);
            
            if (cliente.isPresent() && automovel.isPresent()) {
                Pedido pedido = pedidoService.criarPedido(cliente.get(), automovel.get(), dataInicio, dataFim, valorDiaria);
                return ResponseEntity.ok(pedido);
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
    public ResponseEntity<Pedido> modificarPedido(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id,
            @Parameter(description = "Nova data de início", required = true) @RequestParam LocalDateTime novaDataInicio,
            @Parameter(description = "Nova data de fim", required = true) @RequestParam LocalDateTime novaDataFim) {
        try {
            Optional<Pedido> pedidoModificado = pedidoService.modificarPedido(id, novaDataInicio, novaDataFim);
            return pedidoModificado.map(ResponseEntity::ok)
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
    public ResponseEntity<Pedido> cancelarPedido(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id) {
        try {
            Optional<Pedido> pedidoCancelado = pedidoService.cancelarPedido(id);
            return pedidoCancelado.map(ResponseEntity::ok)
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
    public ResponseEntity<Pedido> enviarParaAnalise(
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.buscarPorId(id);
        if (pedido.isPresent()) {
            pedido.get().enviarParaAnalise();
            Pedido pedidoSalvo = pedidoService.salvar(pedido.get());
            return ResponseEntity.ok(pedidoSalvo);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar pedidos por cliente", description = "Retorna pedidos de um cliente específico")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<Pedido>> listarPorCliente(
            @Parameter(description = "ID do cliente", required = true) @PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar pedidos por status", description = "Retorna pedidos com um status específico")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<Pedido>> listarPorStatus(
            @Parameter(description = "Status do pedido", required = true) @PathVariable String status) {
        try {
            StatusPedido statusPedido = StatusPedido.valueOf(status.toUpperCase());
            List<Pedido> pedidos = pedidoService.buscarPorStatus(statusPedido);
            return ResponseEntity.ok(pedidos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}