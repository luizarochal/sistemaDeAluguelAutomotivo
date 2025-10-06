package com.example.automovel.repository;

import com.example.automovel.model.Pedido;
import com.example.automovel.model.Automovel;
import com.example.automovel.model.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {


    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByStatus(StatusPedido status);

    @Query("SELECT p FROM Pedido p WHERE p.bancoAvaliador.id = :bancoId AND p.status IN :statusList")
    List<Pedido> findByBancoAvaliadorIdAndStatusIn(
            @Param("bancoId") Long bancoId,
            @Param("statusList") List<StatusPedido> statusList);

    List<Pedido> findByAutomovelIn(List<Automovel> automoveis);

    List<Pedido> findByBancoAvaliadorId(Long bancoId);
}
