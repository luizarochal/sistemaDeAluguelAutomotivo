package com.example.automovel.controller;

import com.example.automovel.dto.PedidoDTO;
import com.example.automovel.model.Pedido;
import com.example.automovel.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "API para gerenciamento de pedidos de aluguel")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Listar pedidos")
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar pedido")
    public ResponseEntity<Pedido> criar(@Valid @RequestBody PedidoDTO dto) {
        try {
            Pedido p = pedidoService.criar(dto);
            return ResponseEntity.ok(p);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pedido")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @Valid @RequestBody PedidoDTO dto) {
        try {
            return pedidoService.atualizar(id, dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar pedido")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            boolean ok = pedidoService.deletar(id);
            return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/avaliar")
    @Operation(summary = "Avaliar pedido")
    public ResponseEntity<Pedido> avaliar(@PathVariable Long id, @RequestParam boolean aprovado) {
        return pedidoService.avaliar(id, aprovado).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
