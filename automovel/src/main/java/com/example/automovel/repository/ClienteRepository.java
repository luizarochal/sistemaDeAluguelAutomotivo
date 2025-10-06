package com.example.automovel.repository;

import com.example.automovel.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCPF(String cpf);
    Optional<Cliente> findByRG(String rg);
    boolean existsByCPF(String cpf);
    boolean existsByRG(String rg);
}