package com.example.automovel.controller;

import com.example.automovel.model.Cliente;
import com.example.automovel.dto.ClienteDTO;
import com.example.automovel.service.ClienteService;
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
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "API para gerenciamento de clientes do sistema de aluguel automotivo")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista com todos os clientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico baseado no ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Cliente> buscarPorId(
            @Parameter(description = "ID do cliente", required = true, example = "1")
            @PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        return cliente.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar cliente por CPF", description = "Retorna um cliente específico baseado no CPF")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Cliente> buscarPorCPF(
            @Parameter(description = "CPF do cliente", required = true, example = "123.456.789-00")
            @PathVariable String cpf) {
        Optional<Cliente> cliente = clienteService.buscarPorCPF(cpf);
        return cliente.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Criar novo cliente", description = "Cadastra um novo cliente no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Cliente> criar(
            @Parameter(description = "Dados do cliente para cadastro", required = true)
            @Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente cliente = clienteService.criar(clienteDTO);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Cliente> atualizar(
            @Parameter(description = "ID do cliente a ser atualizado", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Novos dados do cliente", required = true)
            @Valid @RequestBody ClienteDTO clienteDTO) {
        try {
            Optional<Cliente> clienteAtualizado = clienteService.atualizar(id, clienteDTO);
            return clienteAtualizado.map(ResponseEntity::ok)
                                  .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do cliente a ser deletado", required = true, example = "1")
            @PathVariable Long id) {
        boolean deletado = clienteService.deletar(id);
        return deletado ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}