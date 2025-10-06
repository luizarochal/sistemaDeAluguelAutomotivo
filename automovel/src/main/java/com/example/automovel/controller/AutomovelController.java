package com.example.automovel.controller;

import com.example.automovel.model.Automovel;
import com.example.automovel.service.AutomovelService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automoveis")
@Tag(name = "Automóveis", description = "API para gerenciamento de automóveis do sistema de aluguel automotivo")
public class AutomovelController {

    @Autowired
    private AutomovelService automovelService;

    @GetMapping
    public List<Automovel> listar() {
        return automovelService.listarTodos();
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Automovel> buscarPorMatricula(@PathVariable String matricula) {
        return automovelService.buscarPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Automovel a) {
        try {
            Automovel criado = automovelService.criar(a);
            return ResponseEntity.status(201).body(criado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Automovel> atualizar(@PathVariable Long id, @RequestBody Automovel a) {
        return automovelService.atualizar(id, a)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean ok = automovelService.deletar(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
