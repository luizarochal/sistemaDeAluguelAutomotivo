package com.example.automovel.service;

import com.example.automovel.model.Automovel;
import com.example.automovel.repository.AutomovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutomovelService {

    @Autowired
    private AutomovelRepository automovelRepository;

    public List<Automovel> listarTodos() {
        return automovelRepository.findAll();
    }

    public Optional<Automovel> buscarPorId(Long id) {
        return automovelRepository.findById(id);
    }

    public Optional<Automovel> buscarPorMatricula(String matricula) {
        return automovelRepository.findByMatricula(matricula);
    }

    public Automovel criar(Automovel a) {
        // Garantir que qualquer id enviado não cause merge de uma entidade detached
        a.setId(null);
        if (a.getMatricula() != null && automovelRepository.existsByMatricula(a.getMatricula())) {
            throw new IllegalArgumentException("Matrícula já existe: " + a.getMatricula());
        }
        return automovelRepository.save(a);
    }

    public Optional<Automovel> atualizar(Long id, Automovel a) {
        return automovelRepository.findById(id).map(existing -> {
            existing.setMatricula(a.getMatricula());
            existing.setAno(a.getAno());
            existing.setMarca(a.getMarca());
            existing.setModelo(a.getModelo());
            existing.setPlaca(a.getPlaca());
            return automovelRepository.save(existing);
        });
    }

    public boolean deletar(Long id) {
        return automovelRepository.findById(id).map(x -> {
            automovelRepository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
