package com.example.automovel.service;

import com.example.automovel.model.Banco;
import com.example.automovel.dto.BancoDTO;
import com.example.automovel.repository.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    public List<Banco> listarTodos() {
        return bancoRepository.findAll();
    }

    public Optional<Banco> buscarPorId(Long id) {
        return bancoRepository.findById(id);
    }

    public Optional<Banco> buscarPorCodigo(String codigo) {
        return bancoRepository.findByCodigo(codigo);
    }

    public Banco criar(BancoDTO bancoDTO) {

        validarBanco(bancoDTO);

        Banco banco = new Banco(
                bancoDTO.getCodigo(),
                bancoDTO.getNome(),
                bancoDTO.getCentralTelefone());

        return bancoRepository.save(banco);
    }

    public Optional<Banco> atualizar(Long id, BancoDTO bancoDTO) {
        return bancoRepository.findById(id).map(bancoExistente -> {

            validarBanco(bancoDTO);

            bancoExistente.setCodigo(bancoDTO.getCodigo());
            bancoExistente.setNome(bancoDTO.getNome());
            bancoExistente.setCentralTelefone(bancoDTO.getCentralTelefone());

            return bancoRepository.save(bancoExistente);
        });
    }

    public boolean deletar(Long id) {
        if (bancoRepository.existsById(id)) {
            bancoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validarBanco(BancoDTO bancoDTO) {
        if (bancoDTO.getCodigo() == null || bancoDTO.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("Código do banco é obrigatório");
        }

        if (bancoDTO.getNome() == null || bancoDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do banco é obrigatório");
        }
    }
}