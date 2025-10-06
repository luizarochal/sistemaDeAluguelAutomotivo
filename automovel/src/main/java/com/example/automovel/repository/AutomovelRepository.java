package com.example.automovel.repository;

import com.example.automovel.model.Automovel;
import com.example.automovel.model.enums.TipoProprietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, Long> {


    Optional<Automovel> findByPlaca(String placa);

    Optional<Automovel> findByMatricula(String matricula);

    List<Automovel> findByMarca(String marca);

    @Query("SELECT a FROM Automovel a WHERE a.proprietario = :proprietario AND a.empresaProprietaria.id = :empresaId")
    List<Automovel> findByProprietarioAndEmpresaId(
            @Param("proprietario") TipoProprietario proprietario,
            @Param("empresaId") Long empresaId);

    List<Automovel> findByProprietario(TipoProprietario proprietario);

    @Query("SELECT a FROM Automovel a WHERE a.disponivel = true")
    List<Automovel> findDisponiveis();

    List<Automovel> findByMarcaAndModelo(String marca, String modelo);

    List<Automovel> findByAno(Integer ano);

    List<Automovel> findByAnoBetween(Integer anoInicio, Integer anoFim);

    boolean existsByPlaca(String placa);

    boolean existsByMatricula(String matricula);
}

