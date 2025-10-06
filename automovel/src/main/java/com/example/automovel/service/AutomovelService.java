package com.example.automovel.service;

import com.example.automovel.dto.AutomovelDTO;
import com.example.automovel.model.Automovel;
import com.example.automovel.model.Banco;
import com.example.automovel.model.Empresa;
import com.example.automovel.model.enums.TipoProprietario;
import com.example.automovel.repository.AutomovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutomovelService {

    @Autowired
    private AutomovelRepository automovelRepository;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private BancoService bancoService;

    // Métodos CRUD básicos
    public List<Automovel> listarTodos() {
        return automovelRepository.findAll();
    }

    public Optional<Automovel> buscarPorId(Long id) {
        return automovelRepository.findById(id);
    }

    public Automovel criar(Automovel automovel) {
        return automovelRepository.save(automovel);
    }

    public Optional<Automovel> atualizar(Long id, Automovel automovelAtualizado) {
        return automovelRepository.findById(id).map(automovelExistente -> {
            automovelExistente.setMatricula(automovelAtualizado.getMatricula());
            automovelExistente.setAno(automovelAtualizado.getAno());
            automovelExistente.setMarca(automovelAtualizado.getMarca());
            automovelExistente.setModelo(automovelAtualizado.getModelo());
            automovelExistente.setPlaca(automovelAtualizado.getPlaca());
            automovelExistente.setProprietario(automovelAtualizado.getProprietario());
            automovelExistente.setDisponivel(automovelAtualizado.getDisponivel());
            automovelExistente.setEmpresaProprietaria(automovelAtualizado.getEmpresaProprietaria());
            automovelExistente.setBancoProprietario(automovelAtualizado.getBancoProprietario());
            return automovelRepository.save(automovelExistente);
        });
    }

    public boolean deletar(Long id) {
        if (automovelRepository.existsById(id)) {
            automovelRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Métodos de busca específicos
    public Optional<Automovel> buscarPorPlaca(String placa) {
        return automovelRepository.findByPlaca(placa);
    }

    public Optional<Automovel> buscarPorMatricula(String matricula) {
        return automovelRepository.findByMatricula(matricula);
    }


    public List<Automovel> buscarPorMarca(String marca) {
        return automovelRepository.findByMarca(marca);
    }

    public List<Automovel> buscarPorMarcaModelo(String marca, String modelo) {
        return automovelRepository.findByMarcaAndModelo(marca, modelo);
    }

    public List<Automovel> buscarPorAno(Integer ano) {
        return automovelRepository.findByAno(ano);
    }

    public List<Automovel> buscarPorAnoEntre(Integer anoInicio, Integer anoFim) {
        return automovelRepository.findByAnoBetween(anoInicio, anoFim);
    }

    // Métodos para gestão de disponibilidade
    public List<Automovel> listarDisponiveis() {
        return automovelRepository.findDisponiveis();
    }

    public Optional<Automovel> marcarComoIndisponivel(Long id) {
        return automovelRepository.findById(id).map(automovel -> {
            automovel.setDisponivel(false);
            return automovelRepository.save(automovel);
        });
    }

    public Optional<Automovel> marcarComoDisponivel(Long id) {
        return automovelRepository.findById(id).map(automovel -> {
            automovel.setDisponivel(true);
            return automovelRepository.save(automovel);
        });
    }

    // Métodos para gestão de proprietários
    public List<Automovel> buscarPorProprietario(TipoProprietario proprietario) {
        return automovelRepository.findByProprietario(proprietario);
    }

    public List<Automovel> buscarPorEmpresaProprietaria(Long empresaId) {
        return automovelRepository.findByProprietarioAndEmpresaId(TipoProprietario.EMPRESA, empresaId);
    }

    // Validações
    public boolean existePlaca(String placa) {
        return automovelRepository.existsByPlaca(placa);
    }

    public boolean existeMatricula(String matricula) {
        return automovelRepository.existsByMatricula(matricula);
    }

    // Método para criar automóvel com validações
    public Automovel criarAutomovelComValidacao(Automovel automovel) {
        if (existePlaca(automovel.getPlaca())) {
            throw new IllegalArgumentException("Já existe um automóvel com esta placa: " + automovel.getPlaca());
        }
        if (existeMatricula(automovel.getMatricula())) {
            throw new IllegalArgumentException(
                    "Já existe um automóvel com esta matrícula: " + automovel.getMatricula());
        }
        return automovelRepository.save(automovel);
    }

    public Automovel criarComDTO(AutomovelDTO automovelDTO) {
        // Validações básicas
        if (existePlaca(automovelDTO.getPlaca())) {
            throw new IllegalArgumentException("Já existe um automóvel com esta placa: " + automovelDTO.getPlaca());
        }
        if (existeMatricula(automovelDTO.getMatricula())) {
            throw new IllegalArgumentException(
                    "Já existe um automóvel com esta matrícula: " + automovelDTO.getMatricula());
        }

        // Criar o automóvel
        Automovel automovel = new Automovel();
        automovel.setMatricula(automovelDTO.getMatricula());
        automovel.setAno(automovelDTO.getAno());
        automovel.setMarca(automovelDTO.getMarca());
        automovel.setModelo(automovelDTO.getModelo());
        automovel.setPlaca(automovelDTO.getPlaca());
        automovel.setProprietario(automovelDTO.getProprietario());
        automovel.setDisponivel(automovelDTO.getDisponivel() != null ? automovelDTO.getDisponivel() : true);

        // Definir proprietário baseado no tipo
        if (automovelDTO.getProprietario() == TipoProprietario.EMPRESA
                && automovelDTO.getEmpresaProprietariaId() != null) {
            // Buscar empresa no banco de dados
            Empresa empresa = empresaService.buscarPorId(automovelDTO.getEmpresaProprietariaId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Empresa não encontrada com ID: " + automovelDTO.getEmpresaProprietariaId()));
            automovel.setEmpresaProprietaria(empresa);
        } else if (automovelDTO.getProprietario() == TipoProprietario.BANCO
                && automovelDTO.getBancoProprietarioId() != null) {
            // Buscar banco no banco de dados
            Banco banco = bancoService.buscarPorId(automovelDTO.getBancoProprietarioId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Banco não encontrado com ID: " + automovelDTO.getBancoProprietarioId()));
            automovel.setBancoProprietario(banco);
        }

        return automovelRepository.save(automovel);
    }
}
