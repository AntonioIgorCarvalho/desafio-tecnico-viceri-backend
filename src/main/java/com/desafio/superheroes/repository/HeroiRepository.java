package com.desafio.superheroes.repository;

import com.desafio.superheroes.entity.Heroi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeroiRepository extends JpaRepository<Heroi, Integer> {

    /**
     * Verifica se existe um herói com o nome de herói especificado
     */
    boolean existsByNomeHeroi(String nomeHeroi);

    /**
     * Verifica se existe um herói com o nome de herói especificado, excluindo um ID específico
     */
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM Heroi h WHERE h.nomeHeroi = :nomeHeroi AND h.id != :id")
    boolean existsByNomeHeroiAndIdNot(@Param("nomeHeroi") String nomeHeroi, @Param("id") Integer id);

    /**
     * Busca um herói pelo nome do herói
     */
    Optional<Heroi> findByNomeHeroi(String nomeHeroi);

    /**
     * Busca um herói com seus superpoderes carregados
     */
    @Query("SELECT h FROM Heroi h LEFT JOIN FETCH h.heroiSuperpoderes hs LEFT JOIN FETCH hs.superpoder WHERE h.id = :id")
    Optional<Heroi> findByIdWithSuperpoderes(@Param("id") Integer id);
}