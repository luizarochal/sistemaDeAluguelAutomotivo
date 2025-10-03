package com.example.automovel.repository;

import com.example.automovel.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByCNPJ(String cnpj);

    boolean existsByCNPJ(String cnpj);
}