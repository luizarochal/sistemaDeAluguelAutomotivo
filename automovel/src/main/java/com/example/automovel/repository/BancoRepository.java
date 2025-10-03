package com.example.automovel.repository;

import com.example.automovel.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {
    Optional<Banco> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);
}