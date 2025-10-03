package com.example.automovel.controller;

import com.example.automovel.model.Banco;
import com.example.automovel.dto.BancoDTO;
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
}