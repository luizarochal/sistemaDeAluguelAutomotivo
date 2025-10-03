package com.example.automovel.controller;

import com.example.automovel.model.Empresa;
import com.example.automovel.dto.EmpresaDTO;
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
}