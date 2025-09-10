package com.desafio.superheroes.service;

import com.desafio.superheroes.dto.HeroiRequestDTO;
import com.desafio.superheroes.dto.HeroiResponseDTO;
import com.desafio.superheroes.dto.SuperpoderDTO;
import com.desafio.superheroes.entity.Heroi;
import com.desafio.superheroes.entity.HeroiSuperpoder;
import com.desafio.superheroes.entity.Superpoder;
import com.desafio.superheroes.exception.DuplicateHeroiNameException;
import com.desafio.superheroes.exception.HeroiNotFoundException;
import com.desafio.superheroes.repository.HeroiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeroiService {

    @Autowired
    private HeroiRepository heroiRepository;

    @Autowired
    private SuperpoderService superpoderService;

    @Transactional
    public HeroiResponseDTO criar(HeroiRequestDTO heroiRequestDTO) {
        // Verificar se o nome do herói já existe
        if (heroiRepository.existsByNomeHeroi(heroiRequestDTO.getNomeHeroi())) {
            throw new DuplicateHeroiNameException(heroiRequestDTO.getNomeHeroi());
        }

        // Verificar se todos os superpoderes existem
        if (!superpoderService.todosIdsExistem(heroiRequestDTO.getSuperpoderesIds())) {
            throw new IllegalArgumentException("Um ou mais superpoderes informados não existem");
        }

        // Criar o herói
        Heroi heroi = new Heroi(
                heroiRequestDTO.getNome(),
                heroiRequestDTO.getNomeHeroi(),
                heroiRequestDTO.getDataNascimento(),
                heroiRequestDTO.getAltura(),
                heroiRequestDTO.getPeso()
        );

        // Salvar o herói primeiro para gerar o ID
        heroi = heroiRepository.save(heroi);

        // Buscar os superpoderes e associá-los ao herói
        List<Superpoder> superpoderes = superpoderService.buscarPorIds(heroiRequestDTO.getSuperpoderesIds());
        for (Superpoder superpoder : superpoderes) {
            HeroiSuperpoder heroiSuperpoder = new HeroiSuperpoder();
            heroiSuperpoder.setHeroi(heroi);
            heroiSuperpoder.setSuperpoder(superpoder);
            heroiSuperpoder.setHeroiId(heroi.getId());
            heroiSuperpoder.setSuperpoderId(superpoder.getId());
            heroi.getHeroiSuperpoderes().add(heroiSuperpoder);
        }

        // Salvar novamente com os superpoderes
        heroi = heroiRepository.save(heroi);

        return convertToResponseDTO(heroi);
    }

    @Transactional(readOnly = true)
    public List<HeroiResponseDTO> listarTodos() {
        List<Heroi> herois = heroiRepository.findAll();
        if (herois.isEmpty()) {
            throw new HeroiNotFoundException("Nenhum herói encontrado");
        }
        return herois.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public HeroiResponseDTO buscarPorId(Integer id) {
        Heroi heroi = heroiRepository.findByIdWithSuperpoderes(id)
                .orElseThrow(() -> new HeroiNotFoundException(id));
        return convertToResponseDTO(heroi);
    }

    @Transactional
    public HeroiResponseDTO atualizar(Integer id, HeroiRequestDTO heroiRequestDTO) {
        Heroi heroi = heroiRepository.findByIdWithSuperpoderes(id)
                .orElseThrow(() -> new HeroiNotFoundException(id));

        // Verificar se o novo nome do herói já existe (exceto para este herói)
        if (!heroi.getNomeHeroi().equals(heroiRequestDTO.getNomeHeroi()) &&
                heroiRepository.existsByNomeHeroiAndIdNot(heroiRequestDTO.getNomeHeroi(), id)) {
            throw new DuplicateHeroiNameException(heroiRequestDTO.getNomeHeroi());
        }

        // Verificar se todos os superpoderes existem
        if (!superpoderService.todosIdsExistem(heroiRequestDTO.getSuperpoderesIds())) {
            throw new IllegalArgumentException("Um ou mais superpoderes informados não existem");
        }

        // Atualizar os dados básicos do herói
        heroi.setNome(heroiRequestDTO.getNome());
        heroi.setNomeHeroi(heroiRequestDTO.getNomeHeroi());
        heroi.setDataNascimento(heroiRequestDTO.getDataNascimento());
        heroi.setAltura(heroiRequestDTO.getAltura());
        heroi.setPeso(heroiRequestDTO.getPeso());

        // Limpar os superpoderes atuais
        heroi.getHeroiSuperpoderes().clear();

        // Adicionar os novos superpoderes
        List<Superpoder> superpoderes = superpoderService.buscarPorIds(heroiRequestDTO.getSuperpoderesIds());
        for (Superpoder superpoder : superpoderes) {
            HeroiSuperpoder heroiSuperpoder = new HeroiSuperpoder();
            heroiSuperpoder.setHeroi(heroi);
            heroiSuperpoder.setSuperpoder(superpoder);
            heroiSuperpoder.setHeroiId(heroi.getId());
            heroiSuperpoder.setSuperpoderId(superpoder.getId());
            heroi.getHeroiSuperpoderes().add(heroiSuperpoder);
        }

        heroi = heroiRepository.save(heroi);
        return convertToResponseDTO(heroi);
    }

    @Transactional
    public void deletar(Integer id) {
        Heroi heroi = heroiRepository.findById(id)
                .orElseThrow(() -> new HeroiNotFoundException(id));
        heroiRepository.delete(heroi);
    }

    private HeroiResponseDTO convertToResponseDTO(Heroi heroi) {
        List<SuperpoderDTO> superpoderesDTO = heroi.getHeroiSuperpoderes().stream()
                .map(hs -> new SuperpoderDTO(
                        hs.getSuperpoder().getId(),
                        hs.getSuperpoder().getSuperpoder(),
                        hs.getSuperpoder().getDescricao()
                ))
                .collect(Collectors.toList());

        return new HeroiResponseDTO(
                heroi.getId(),
                heroi.getNome(),
                heroi.getNomeHeroi(),
                heroi.getDataNascimento(),
                heroi.getAltura(),
                heroi.getPeso(),
                superpoderesDTO
        );
    }
}


