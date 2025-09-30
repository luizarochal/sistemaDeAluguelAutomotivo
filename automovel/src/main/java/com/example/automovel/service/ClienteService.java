package com.example.automovel.service;

import com.example.automovel.model.Cliente;
import com.example.automovel.dto.ClienteDTO;
import com.example.automovel.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    private static final int MAX_EMPRESAS = 3;
    
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
    
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }
    
    public Optional<Cliente> buscarPorCPF(String cpf) {
        return clienteRepository.findByCPF(cpf);
    }
    
    public Cliente criar(ClienteDTO clienteDTO) {
        // Validações
        validarCliente(clienteDTO);
        
        Cliente cliente = new Cliente(
            clienteDTO.getRG(),
            clienteDTO.getCPF(),
            clienteDTO.getNome(),
            clienteDTO.getEndereco(),
            clienteDTO.getProfissao(),
            clienteDTO.getEntidadesEmpregadoras()
        );
        
        return clienteRepository.save(cliente);
    }
    
    public Optional<Cliente> atualizar(Long id, ClienteDTO clienteDTO) {
        return clienteRepository.findById(id).map(clienteExistente -> {
            // Validações
            validarCliente(clienteDTO);
            
            clienteExistente.setRG(clienteDTO.getRG());
            clienteExistente.setCPF(clienteDTO.getCPF());
            clienteExistente.setNome(clienteDTO.getNome());
            clienteExistente.setEndereco(clienteDTO.getEndereco());
            clienteExistente.setProfissao(clienteDTO.getProfissao());
            clienteExistente.setEntidadesEmpregadoras(clienteDTO.getEntidadesEmpregadoras());
            
            return clienteRepository.save(clienteExistente);
        });
    }
    
    public boolean deletar(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    private void validarCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.getEntidadesEmpregadoras() != null && 
            clienteDTO.getEntidadesEmpregadoras().size() > MAX_EMPRESAS) {
            throw new IllegalArgumentException("Máximo de " + MAX_EMPRESAS + " entidades empregadoras permitidas");
        }
        
        if (clienteDTO.getCPF() == null || clienteDTO.getCPF().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }
        
        if (clienteDTO.getRG() == null || clienteDTO.getRG().trim().isEmpty()) {
            throw new IllegalArgumentException("RG é obrigatório");
        }
        
        if (clienteDTO.getNome() == null || clienteDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
    }
}