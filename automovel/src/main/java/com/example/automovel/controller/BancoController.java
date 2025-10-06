package com.example.automovel.controller;

import com.example.automovel.model.Banco;
import com.example.automovel.model.Pedido;
import com.example.automovel.model.ContratoCredito;
import com.example.automovel.dto.BancoDTO;
import com.example.automovel.dto.EstatisticasBancoDTO;
import com.example.automovel.dto.AvaliacaoFinanceiraDTO;
import com.example.automovel.service.BancoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bancos")
@Tag(name = "Bancos", description = "API para gerenciamento de bancos do sistema de aluguel automotivo")
public class BancoController {

    @Autowired
    private BancoService bancoService;

    @GetMapping
    @Operation(summary = "Listar todos os bancos", description = "Retorna uma lista com todos os bancos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de bancos retornada com sucesso")
    public ResponseEntity<List<Banco>> listarTodos() {
        List<Banco> bancos = bancoService.listarTodos();
        return ResponseEntity.ok(bancos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar banco por ID", description = "Retorna um banco específico baseado no ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Banco encontrado"),
            @ApiResponse(responseCode = "404", description = "Banco não encontrado")
    })
    public ResponseEntity<Banco> buscarPorId(
            @Parameter(description = "ID do banco", required = true, example = "1") @PathVariable Long id) {
        Optional<Banco> banco = bancoService.buscarPorId(id);
        return banco.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar banco por Código", description = "Retorna um banco específico baseado no Código")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Banco encontrado"),
            @ApiResponse(responseCode = "404", description = "Banco não encontrado")
    })
    public ResponseEntity<Banco> buscarPorCodigo(
            @Parameter(description = "Código do banco", required = true, example = "001") @PathVariable String codigo) {
        Optional<Banco> banco = bancoService.buscarPorCodigo(codigo);
        return banco.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo banco", description = "Cadastra um novo banco no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Banco criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Banco> criar(
            @Parameter(description = "Dados do banco para cadastro", required = true) @Valid @RequestBody BancoDTO bancoDTO) {
        try {
            Banco banco = bancoService.criar(bancoDTO);
            return ResponseEntity.ok(banco);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar banco", description = "Atualiza os dados de um banco existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Banco atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Banco não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Banco> atualizar(
            @Parameter(description = "ID do banco a ser atualizado", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Novos dados do banco", required = true) @Valid @RequestBody BancoDTO bancoDTO) {
        try {
            Optional<Banco> bancoAtualizado = bancoService.atualizar(id, bancoDTO);
            return bancoAtualizado.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar banco", description = "Remove um banco do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Banco deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Banco não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do banco a ser deletado", required = true, example = "1") @PathVariable Long id) {
        boolean deletado = bancoService.deletar(id);
        return deletado ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{bancoId}/avaliar-pedido/{pedidoId}")
    @Operation(summary = "Avaliar pedido financeiramente", description = "Banco avalia um pedido do ponto de vista financeiro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido avaliado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Banco ou pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Pedido> avaliarPedido(
            @Parameter(description = "ID do banco", required = true) @PathVariable Long bancoId,
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long pedidoId,
            @Parameter(description = "Dados da avaliação", required = true) @Valid @RequestBody AvaliacaoFinanceiraDTO avaliacaoDTO) {
        try {
            avaliacaoDTO.setBancoId(bancoId);
            Optional<Pedido> pedidoAvaliado = bancoService.avaliarPedido(pedidoId, avaliacaoDTO);
            return pedidoAvaliado.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/pedidos-para-avaliacao")
    @Operation(summary = "Listar pedidos para avaliação", description = "Retorna pedidos pendentes de avaliação financeira")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<Pedido>> listarPedidosParaAvaliacao(
            @Parameter(description = "ID do banco", required = true) @PathVariable Long id) {
        List<Pedido> pedidos = bancoService.listarPedidosParaAvaliacao();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}/pedidos-avaliados")
    @Operation(summary = "Listar pedidos avaliados", description = "Retorna pedidos já avaliados por este banco")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<Pedido>> listarPedidosAvaliados(
            @Parameter(description = "ID do banco", required = true) @PathVariable Long id) {
        List<Pedido> pedidos = bancoService.listarPedidosAvaliados(id);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}/contratos-credito")
    @Operation(summary = "Listar contratos de crédito", description = "Retorna contratos de crédito concedidos pelo banco")
    @ApiResponse(responseCode = "200", description = "Lista de contratos retornada com sucesso")
    public ResponseEntity<List<ContratoCredito>> listarContratosCredito(
            @Parameter(description = "ID do banco", required = true) @PathVariable Long id) {
        List<ContratoCredito> contratos = bancoService.listarContratosCredito(id);
        return ResponseEntity.ok(contratos);
    }

    @GetMapping("/{id}/estatisticas")
    @Operation(summary = "Obter estatísticas do banco", description = "Retorna estatísticas financeiras do banco")
    @ApiResponse(responseCode = "200", description = "Estatísticas retornadas com sucesso")
    public ResponseEntity<EstatisticasBancoDTO> obterEstatisticas(
            @Parameter(description = "ID do banco", required = true) @PathVariable Long id) {
        EstatisticasBancoDTO estatisticas = bancoService.obterEstatisticas(id);
        return ResponseEntity.ok(estatisticas);
    }

    @PutMapping("/{bancoId}/modificar-avaliacao/{pedidoId}")
    @Operation(summary = "Modificar avaliação de pedido", description = "Banco modifica a avaliação de um pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avaliação modificada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Banco ou pedido não encontrado")
    })
    public ResponseEntity<Pedido> modificarAvaliacao(
            @Parameter(description = "ID do banco", required = true) @PathVariable Long bancoId,
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long pedidoId,
            @Parameter(description = "Novo parecer", required = true) @RequestParam String novoParecer) {
        Optional<Pedido> pedidoModificado = bancoService.modificarAvaliacaoPedido(pedidoId, bancoId, novoParecer);
        return pedidoModificado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}