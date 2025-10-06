package com.example.automovel.controller;


import com.example.automovel.dto.AutomovelDTO;
import com.example.automovel.model.Automovel;
import com.example.automovel.model.enums.TipoProprietario;
import com.example.automovel.service.AutomovelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/automoveis")
@Tag(name = "Automóveis", description = "API para gerenciamento de automóveis do sistema de aluguel automotivo")
public class AutomovelController {

    @Autowired
    private AutomovelService automovelService;

    @GetMapping

    @Operation(summary = "Listar todos os automóveis", description = "Retorna uma lista com todos os automóveis cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de automóveis retornada com sucesso")
    public ResponseEntity<List<Automovel>> listarTodos() {
        List<Automovel> automoveis = automovelService.listarTodos();
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar automóvel por ID", description = "Retorna um automóvel específico baseado no ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Automóvel encontrado"),
            @ApiResponse(responseCode = "404", description = "Automóvel não encontrado")
    })
    public ResponseEntity<Automovel> buscarPorId(
            @Parameter(description = "ID do automóvel", required = true, example = "1") @PathVariable Long id) {
        Optional<Automovel> automovel = automovelService.buscarPorId(id);
        return automovel.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/placa/{placa}")
    @Operation(summary = "Buscar automóvel por placa", description = "Retorna um automóvel específico baseado na placa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Automóvel encontrado"),
            @ApiResponse(responseCode = "404", description = "Automóvel não encontrado")
    })
    public ResponseEntity<Automovel> buscarPorPlaca(
            @Parameter(description = "Placa do automóvel", required = true, example = "ABC-1234") @PathVariable String placa) {
        Optional<Automovel> automovel = automovelService.buscarPorPlaca(placa);
        return automovel.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/matricula/{matricula}")
    @Operation(summary = "Buscar automóvel por matrícula", description = "Retorna um automóvel específico baseado na matrícula")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Automóvel encontrado"),
            @ApiResponse(responseCode = "404", description = "Automóvel não encontrado")
    })
    public ResponseEntity<Automovel> buscarPorMatricula(
            @Parameter(description = "Matrícula do automóvel", required = true, example = "MAT123456") @PathVariable String matricula) {
        Optional<Automovel> automovel = automovelService.buscarPorMatricula(matricula);
        return automovel.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo automóvel", description = "Cadastra um novo automóvel no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Automóvel criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Automovel> criar(@RequestBody AutomovelDTO automovelDTO) {
        try {
            Automovel automovelCriado = automovelService.criarComDTO(automovelDTO);
            return ResponseEntity.ok(automovelCriado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")

    @Operation(summary = "Atualizar automóvel", description = "Atualiza os dados de um automóvel existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Automóvel atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Automóvel não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Automovel> atualizar(
            @Parameter(description = "ID do automóvel a ser atualizado", required = true, example = "1") @PathVariable Long id,
            @RequestBody Automovel automovel) {
        try {
            Optional<Automovel> automovelAtualizado = automovelService.atualizar(id, automovel);
            return automovelAtualizado.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar automóvel", description = "Remove um automóvel do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Automóvel deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Automóvel não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do automóvel a ser deletado", required = true, example = "1") @PathVariable Long id) {
        boolean deletado = automovelService.deletar(id);
        return deletado ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/marca/{marca}")
    @Operation(summary = "Listar automóveis por marca", description = "Retorna automóveis de uma marca específica")
    @ApiResponse(responseCode = "200", description = "Lista de automóveis retornada com sucesso")
    public ResponseEntity<List<Automovel>> buscarPorMarca(
            @Parameter(description = "Marca do automóvel", required = true, example = "Toyota") @PathVariable String marca) {
        List<Automovel> automoveis = automovelService.buscarPorMarca(marca);
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/marca/{marca}/modelo/{modelo}")
    @Operation(summary = "Listar automóveis por marca e modelo", description = "Retorna automóveis de uma marca e modelo específicos")
    @ApiResponse(responseCode = "200", description = "Lista de automóveis retornada com sucesso")
    public ResponseEntity<List<Automovel>> buscarPorMarcaModelo(
            @Parameter(description = "Marca do automóvel", required = true, example = "Toyota") @PathVariable String marca,
            @Parameter(description = "Modelo do automóvel", required = true, example = "Corolla") @PathVariable String modelo) {
        List<Automovel> automoveis = automovelService.buscarPorMarcaModelo(marca, modelo);
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/ano/{ano}")
    @Operation(summary = "Listar automóveis por ano", description = "Retorna automóveis de um ano específico")
    @ApiResponse(responseCode = "200", description = "Lista de automóveis retornada com sucesso")
    public ResponseEntity<List<Automovel>> buscarPorAno(
            @Parameter(description = "Ano do automóvel", required = true, example = "2023") @PathVariable Integer ano) {
        List<Automovel> automoveis = automovelService.buscarPorAno(ano);
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/ano/{anoInicio}/{anoFim}")
    @Operation(summary = "Listar automóveis por intervalo de ano", description = "Retorna automóveis em um intervalo de anos")
    @ApiResponse(responseCode = "200", description = "Lista de automóveis retornada com sucesso")
    public ResponseEntity<List<Automovel>> buscarPorAnoEntre(
            @Parameter(description = "Ano inicial", required = true, example = "2020") @PathVariable Integer anoInicio,
            @Parameter(description = "Ano final", required = true, example = "2023") @PathVariable Integer anoFim) {
        List<Automovel> automoveis = automovelService.buscarPorAnoEntre(anoInicio, anoFim);
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar automóveis disponíveis", description = "Retorna automóveis disponíveis para aluguel")
    @ApiResponse(responseCode = "200", description = "Lista de automóveis disponíveis retornada com sucesso")
    public ResponseEntity<List<Automovel>> listarDisponiveis() {
        List<Automovel> automoveis = automovelService.listarDisponiveis();
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/proprietario/{proprietario}")
    @Operation(summary = "Listar automóveis por tipo de proprietário", description = "Retorna automóveis por tipo de proprietário (CLIENTE, EMPRESA, BANCO)")
    @ApiResponse(responseCode = "200", description = "Lista de automóveis retornada com sucesso")
    public ResponseEntity<List<Automovel>> buscarPorProprietario(
            @Parameter(description = "Tipo de proprietário", required = true, example = "EMPRESA") @PathVariable TipoProprietario proprietario) {
        List<Automovel> automoveis = automovelService.buscarPorProprietario(proprietario);
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Listar automóveis por empresa proprietária", description = "Retorna automóveis pertencentes a uma empresa específica")
    @ApiResponse(responseCode = "200", description = "Lista de automóveis retornada com sucesso")
    public ResponseEntity<List<Automovel>> buscarPorEmpresaProprietaria(
            @Parameter(description = "ID da empresa", required = true, example = "1") @PathVariable Long empresaId) {
        List<Automovel> automoveis = automovelService.buscarPorEmpresaProprietaria(empresaId);
        return ResponseEntity.ok(automoveis);
    }

    @PutMapping("/{id}/disponivel")
    @Operation(summary = "Marcar automóvel como disponível", description = "Marca um automóvel como disponível para aluguel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Automóvel marcado como disponível"),
            @ApiResponse(responseCode = "404", description = "Automóvel não encontrado")
    })
    public ResponseEntity<Automovel> marcarComoDisponivel(
            @Parameter(description = "ID do automóvel", required = true, example = "1") @PathVariable Long id) {
        Optional<Automovel> automovel = automovelService.marcarComoDisponivel(id);
        return automovel.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/indisponivel")
    @Operation(summary = "Marcar automóvel como indisponível", description = "Marca um automóvel como indisponível para aluguel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Automóvel marcado como indisponível"),
            @ApiResponse(responseCode = "404", description = "Automóvel não encontrado")
    })
    public ResponseEntity<Automovel> marcarComoIndisponivel(
            @Parameter(description = "ID do automóvel", required = true, example = "1") @PathVariable Long id) {
        Optional<Automovel> automovel = automovelService.marcarComoIndisponivel(id);
        return automovel.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/verificar-placa/{placa}")
    @Operation(summary = "Verificar existência de placa", description = "Verifica se já existe um automóvel com a placa informada")
    @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    public ResponseEntity<Boolean> verificarPlaca(
            @Parameter(description = "Placa a ser verificada", required = true, example = "ABC-1234") @PathVariable String placa) {
        boolean existe = automovelService.existePlaca(placa);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/verificar-matricula/{matricula}")
    @Operation(summary = "Verificar existência de matrícula", description = "Verifica se já existe um automóvel com a matrícula informada")
    @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    public ResponseEntity<Boolean> verificarMatricula(
            @Parameter(description = "Matrícula a ser verificada", required = true, example = "MAT123456") @PathVariable String matricula) {
        boolean existe = automovelService.existeMatricula(matricula);
        return ResponseEntity.ok(existe);
    }
}
