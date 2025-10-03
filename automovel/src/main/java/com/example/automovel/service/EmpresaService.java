package com.example.automovel.service;

import com.example.automovel.model.Empresa;
import com.example.automovel.dto.EmpresaDTO;
import com.example.automovel.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final int MAX_SOCIOS = 5;

    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> buscarPorId(Long id) {
        return empresaRepository.findById(id);
    }

    public Optional<Empresa> buscarPorCNPJ(String cnpj) {
        return empresaRepository.findByCNPJ(cnpj);
    }

    public Empresa criar(EmpresaDTO empresaDTO) {
        // Validações
        validarEmpresa(empresaDTO);

        Empresa empresa = new Empresa(
                empresaDTO.getCNPJ(),
                empresaDTO.getNome(),
                empresaDTO.getEndereco(),
                empresaDTO.getTelefone(),
                empresaDTO.getSocios());

        return empresaRepository.save(empresa);
    }

    public Optional<Empresa> atualizar(Long id, EmpresaDTO empresaDTO) {
        return empresaRepository.findById(id).map(empresaExistente -> {

            validarEmpresa(empresaDTO);

            empresaExistente.setCNPJ(empresaDTO.getCNPJ());
            empresaExistente.setNome(empresaDTO.getNome());
            empresaExistente.setEndereco(empresaDTO.getEndereco());
            empresaExistente.setTelefone(empresaDTO.getTelefone());
            empresaExistente.setSocios(empresaDTO.getSocios());

            return empresaRepository.save(empresaExistente);
        });
    }

    public boolean deletar(Long id) {
        if (empresaRepository.existsById(id)) {
            empresaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validarEmpresa(EmpresaDTO empresaDTO) {
        if (empresaDTO.getSocios() != null &&
                empresaDTO.getSocios().size() > MAX_SOCIOS) {
            throw new IllegalArgumentException("Máximo de " + MAX_SOCIOS + " sócios permitidos");
        }

        if (empresaDTO.getCNPJ() == null || empresaDTO.getCNPJ().trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ é obrigatório");
        }

        if (empresaDTO.getNome() == null || empresaDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
    }
}