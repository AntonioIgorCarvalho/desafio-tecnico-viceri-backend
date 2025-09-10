package com.desafio.superheroes.controller;

import com.desafio.superheroes.dto.HeroiRequestDTO;
import com.desafio.superheroes.dto.HeroiResponseDTO;
import com.desafio.superheroes.dto.SuperpoderDTO;
import com.desafio.superheroes.service.HeroiService;
import com.desafio.superheroes.service.SuperpoderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/herois")
@CrossOrigin(origins = "*")
public class HeroiController {

    @Autowired
    private HeroiService heroiService;

    @Autowired
    private SuperpoderService superpoderService;

    /**
     * Criar um novo herói
     */
    @PostMapping
    public ResponseEntity<HeroiResponseDTO> criarHeroi(@Valid @RequestBody HeroiRequestDTO heroiRequestDTO) {
        HeroiResponseDTO heroiCriado = heroiService.criar(heroiRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(heroiCriado);
    }

    /**
     * Listar todos os heróis
     */
    @GetMapping
    public ResponseEntity<List<HeroiResponseDTO>> listarHerois() {
        List<HeroiResponseDTO> herois = heroiService.listarTodos();
        return ResponseEntity.ok(herois);
    }

    /**
     * Buscar herói por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<HeroiResponseDTO> buscarHeroiPorId(@PathVariable Integer id) {
        HeroiResponseDTO heroi = heroiService.buscarPorId(id);
        return ResponseEntity.ok(heroi);
    }

    /**
     * Atualizar herói por ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizarHeroi(
            @PathVariable Integer id,
            @Valid @RequestBody HeroiRequestDTO heroiRequestDTO) {
        HeroiResponseDTO heroiAtualizado = heroiService.atualizar(id, heroiRequestDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Herói atualizado com sucesso");
        response.put("heroi", heroiAtualizado);

        return ResponseEntity.ok(response);
    }

    /**
     * Deletar herói por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletarHeroi(@PathVariable Integer id) {
        heroiService.deletar(id);

        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Herói excluído com sucesso");

        return ResponseEntity.ok(response);
    }

    /**
     * Listar todos os superpoderes disponíveis
     */
    @GetMapping("/superpoderes")
    public ResponseEntity<List<SuperpoderDTO>> listarSuperpoderes() {
        List<SuperpoderDTO> superpoderes = superpoderService.listarTodos();
        return ResponseEntity.ok(superpoderes);
    }
}
