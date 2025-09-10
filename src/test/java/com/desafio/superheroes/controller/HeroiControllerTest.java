package com.desafio.superheroes.controller;

import com.desafio.superheroes.dto.HeroiRequestDTO;
import com.desafio.superheroes.dto.HeroiResponseDTO;
import com.desafio.superheroes.dto.SuperpoderDTO;
import com.desafio.superheroes.exception.HeroiNotFoundException;
import com.desafio.superheroes.service.HeroiService;
import com.desafio.superheroes.service.SuperpoderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HeroiController.class)
class HeroiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HeroiService heroiService;

    @MockitoBean
    private SuperpoderService superpoderService;

    @Autowired
    private ObjectMapper objectMapper;

    private HeroiRequestDTO heroiRequestDTO;
    private HeroiResponseDTO heroiResponseDTO;

    @BeforeEach
    void setUp() {
        heroiRequestDTO = new HeroiRequestDTO(
                "Clark Kent",
                "Superman",
                LocalDateTime.of(1938, 6, 1, 0, 0),
                1.91,
                102.0,
                Arrays.asList(1, 2)
        );

        heroiResponseDTO = new HeroiResponseDTO(
                1,
                "Clark Kent",
                "Superman",
                LocalDateTime.of(1938, 6, 1, 0, 0),
                1.91,
                102.0,
                Arrays.asList(new SuperpoderDTO(1, "Super Força", "Descrição"))
        );
    }

    @Test
    void deveCriarHeroiComSucesso() throws Exception {
        // Arrange
        when(heroiService.criar(any(HeroiRequestDTO.class))).thenReturn(heroiResponseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/herois")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroiRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nomeHeroi").value("Superman"));

        verify(heroiService).criar(any(HeroiRequestDTO.class));
    }

    @Test
    void deveListarHeroisComSucesso() throws Exception {
        // Arrange
        List<HeroiResponseDTO> herois = Arrays.asList(heroiResponseDTO);
        when(heroiService.listarTodos()).thenReturn(herois);

        // Act & Assert
        mockMvc.perform(get("/api/herois"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nomeHeroi").value("Superman"));

        verify(heroiService).listarTodos();
    }

    @Test
    void deveBuscarHeroiPorIdComSucesso() throws Exception {
        // Arrange
        when(heroiService.buscarPorId(anyInt())).thenReturn(heroiResponseDTO);

        // Act & Assert
        mockMvc.perform(get("/api/herois/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nomeHeroi").value("Superman"));

        verify(heroiService).buscarPorId(1);
    }

    @Test
    void deveRetornarNotFoundQuandoHeroiNaoExiste() throws Exception {
        // Arrange
        when(heroiService.buscarPorId(anyInt())).thenThrow(new HeroiNotFoundException(999));

        // Act & Assert
        mockMvc.perform(get("/api/herois/999"))
                .andExpect(status().isNotFound());

        verify(heroiService).buscarPorId(999);
    }

    @Test
    void deveAtualizarHeroiComSucesso() throws Exception {
        // Arrange
        when(heroiService.atualizar(anyInt(), any(HeroiRequestDTO.class))).thenReturn(heroiResponseDTO);

        // Act & Assert
        mockMvc.perform(put("/api/herois/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroiRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Herói atualizado com sucesso"))
                .andExpect(jsonPath("$.heroi.nomeHeroi").value("Superman"));

        verify(heroiService).atualizar(eq(1), any(HeroiRequestDTO.class));
    }

    @Test
    void deveDeletarHeroiComSucesso() throws Exception {
        // Arrange
        doNothing().when(heroiService).deletar(anyInt());

        // Act & Assert
        mockMvc.perform(delete("/api/herois/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Herói excluído com sucesso"));

        verify(heroiService).deletar(1);
    }

    @Test
    void deveListarSuperpoderesComSucesso() throws Exception {
        // Arrange
        List<SuperpoderDTO> superpoderes = Arrays.asList(
                new SuperpoderDTO(1, "Super Força", "Descrição 1"),
                new SuperpoderDTO(2, "Voo", "Descrição 2")
        );
        when(superpoderService.listarTodos()).thenReturn(superpoderes);

        // Act & Assert
        mockMvc.perform(get("/api/herois/superpoderes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].superpoder").value("Super Força"))
                .andExpect(jsonPath("$[1].superpoder").value("Voo"));

        verify(superpoderService).listarTodos();
    }

    @Test
    void deveRetornarBadRequestParaDadosInvalidos() throws Exception {
        // Arrange
        HeroiRequestDTO heroiInvalido = new HeroiRequestDTO();
        heroiInvalido.setNome(""); // Nome vazio - inválido
        heroiInvalido.setNomeHeroi(""); // Nome herói vazio - inválido

        // Act & Assert
        mockMvc.perform(post("/api/herois")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroiInvalido)))
                .andExpect(status().isBadRequest());

        verify(heroiService, never()).criar(any(HeroiRequestDTO.class));
    }
}