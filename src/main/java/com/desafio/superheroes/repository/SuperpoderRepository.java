package com.desafio.superheroes.repository;

import com.desafio.superheroes.entity.Superpoder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperpoderRepository extends JpaRepository<Superpoder, Integer> {

    /**
     * Busca superpoderes pelos IDs
     */
    List<Superpoder> findByIdIn(List<Integer> ids);

    /**
     * Verifica se todos os IDs de superpoderes existem
     */
    long countByIdIn(List<Integer> ids);
}
