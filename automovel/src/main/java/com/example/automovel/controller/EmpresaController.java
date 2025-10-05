package com.example.automovel.controller;

import com.example.automovel.model.Empresa;
import com.example.automovel.model.Pedido;
import com.example.automovel.model.Automovel;
import com.example.automovel.dto.EmpresaDTO;
import com.example.automovel.dto.EstatisticasEmpresaDTO;
import com.example.automovel.service.EmpresaService;
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
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "API para gerenciamento de empresas do sistema de aluguel automotivo")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    @Operation(summary = "Listar todas as empresas", description = "Retorna uma lista com todas as empresas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de empresas retornada com sucesso")
    public ResponseEntity<List<Empresa>> listarTodas() {
        List<Empresa> empresas = empresaService.listarTodas();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empresa por ID", description = "Retorna uma empresa específica baseada no ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    public ResponseEntity<Empresa> buscarPorId(
            @Parameter(description = "ID da empresa", required = true, example = "1") @PathVariable Long id) {
        Optional<Empresa> empresa = empresaService.buscarPorId(id);
        return empresa.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cnpj/{cnpj}")
    @Operation(summary = "Buscar empresa por CNPJ", description = "Retorna uma empresa específica baseada no CNPJ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    public ResponseEntity<Empresa> buscarPorCNPJ(
            @Parameter(description = "CNPJ da empresa", required = true, example = "12.345.678/0001-90") @PathVariable String cnpj) {
        Optional<Empresa> empresa = empresaService.buscarPorCNPJ(cnpj);
        return empresa.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar nova empresa", description = "Cadastra uma nova empresa no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Empresa> criar(
            @Parameter(description = "Dados da empresa para cadastro", required = true) @Valid @RequestBody EmpresaDTO empresaDTO) {
        try {
            Empresa empresa = empresaService.criar(empresaDTO);
            return ResponseEntity.ok(empresa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar empresa", description = "Atualiza os dados de uma empresa existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Empresa> atualizar(
            @Parameter(description = "ID da empresa a ser atualizada", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Novos dados da empresa", required = true) @Valid @RequestBody EmpresaDTO empresaDTO) {
        try {
            Optional<Empresa> empresaAtualizada = empresaService.atualizar(id, empresaDTO);
            return empresaAtualizada.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar empresa", description = "Remove uma empresa do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empresa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da empresa a ser deletada", required = true, example = "1") @PathVariable Long id) {
        boolean deletada = empresaService.deletar(id);
        return deletada ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{empresaId}/avaliar-pedido/{pedidoId}")
    @Operation(summary = "Avaliar pedido comercialmente", description = "Empresa avalia um pedido do ponto de vista comercial/operacional")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido avaliado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa ou pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Pedido> avaliarPedidoComercialmente(
            @Parameter(description = "ID da empresa", required = true) @PathVariable Long empresaId,
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long pedidoId,
            @Parameter(description = "Parecer comercial", required = true) @RequestParam String parecer,
            @Parameter(description = "Status de aprovação", required = true) @RequestParam boolean aprovado) {
        try {
            Optional<Pedido> pedidoAvaliado = empresaService.avaliarPedidoComercialmente(pedidoId, empresaId, parecer, aprovado);
            return pedidoAvaliado.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/automoveis")
    @Operation(summary = "Listar automóveis da empresa", description = "Retorna automóveis pertencentes à empresa")
    @ApiResponse(responseCode = "200", description = "Lista de automóveis retornada com sucesso")
    public ResponseEntity<List<Automovel>> listarAutomoveisEmpresa(
            @Parameter(description = "ID da empresa", required = true) @PathVariable Long id) {
        List<Automovel> automoveis = empresaService.listarAutomoveisEmpresa(id);
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/{id}/pedidos")
    @Operation(summary = "Listar pedidos dos automóveis da empresa", description = "Retorna pedidos relacionados aos automóveis da empresa")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<Pedido>> listarPedidosAutomoveisEmpresa(
            @Parameter(description = "ID da empresa", required = true) @PathVariable Long id) {
        List<Pedido> pedidos = empresaService.listarPedidosAutomoveisEmpresa(id);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}/estatisticas")
    @Operation(summary = "Obter estatísticas da empresa", description = "Retorna estatísticas comerciais da empresa")
    @ApiResponse(responseCode = "200", description = "Estatísticas retornadas com sucesso")
    public ResponseEntity<EstatisticasEmpresaDTO> obterEstatisticas(
            @Parameter(description = "ID da empresa", required = true) @PathVariable Long id) {
        EstatisticasEmpresaDTO estatisticas = empresaService.obterEstatisticas(id);
        return ResponseEntity.ok(estatisticas);
    }

    @PutMapping("/{empresaId}/modificar-avaliacao/{pedidoId}")
    @Operation(summary = "Modificar avaliação comercial", description = "Empresa modifica a avaliação comercial de um pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Avaliação modificada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa ou pedido não encontrado")
    })
    public ResponseEntity<Pedido> modificarAvaliacaoComercial(
            @Parameter(description = "ID da empresa", required = true) @PathVariable Long empresaId,
            @Parameter(description = "ID do pedido", required = true) @PathVariable Long pedidoId,
            @Parameter(description = "Novo parecer comercial", required = true) @RequestParam String novoParecer) {
        Optional<Pedido> pedidoModificado = empresaService.modificarAvaliacaoComercial(pedidoId, empresaId, novoParecer);
        return pedidoModificado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}