package com.example.automovel.repository;

import com.example.automovel.model.Contrato;
import com.example.automovel.model.enums.StatusContrato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    List<Contrato> findByClienteId(Long clienteId);
    List<Contrato> findByStatus(StatusContrato status);
    
    @Query("SELECT c FROM Contrato c WHERE TYPE(c) = Contrato")
    List<Contrato> findContratosNormais();
}