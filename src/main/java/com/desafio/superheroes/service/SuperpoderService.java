package com.desafio.superheroes.service;

import com.desafio.superheroes.dto.SuperpoderDTO;
import com.desafio.superheroes.entity.Superpoder;
import com.desafio.superheroes.repository.SuperpoderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuperpoderService {

    @Autowired
    private SuperpoderRepository superpoderRepository;

    @Transactional(readOnly = true)
    public List<SuperpoderDTO> listarTodos() {
        return superpoderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Superpoder> buscarPorIds(List<Integer> ids) {
        return superpoderRepository.findByIdIn(ids);
    }

    @Transactional(readOnly = true)
    public boolean todosIdsExistem(List<Integer> ids) {
        long count = superpoderRepository.countByIdIn(ids);
        return count == ids.size();
    }

    private SuperpoderDTO convertToDTO(Superpoder superpoder) {
        return new SuperpoderDTO(
                superpoder.getId(),
                superpoder.getSuperpoder(),
                superpoder.getDescricao()
        );
    }
}
