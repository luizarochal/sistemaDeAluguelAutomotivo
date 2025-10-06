package com.example.automovel.repository;

import com.example.automovel.model.Automovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    boolean existsByMatricula(String matricula);
    Optional<Automovel> findByMatricula(String matricula);
}
