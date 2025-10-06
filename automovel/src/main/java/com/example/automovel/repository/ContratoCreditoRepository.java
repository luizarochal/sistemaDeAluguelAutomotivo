package com.example.automovel.repository;

import com.example.automovel.model.ContratoCredito;
import com.example.automovel.model.enums.StatusContratoCredito;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ContratoCreditoRepository extends JpaRepository<ContratoCredito, Long> {
    List<ContratoCredito> findByBancoId(Long bancoId);
    Optional<ContratoCredito> findByPedidoId(Long pedidoId);
    List<ContratoCredito> findByStatusCredito(StatusContratoCredito status);
}