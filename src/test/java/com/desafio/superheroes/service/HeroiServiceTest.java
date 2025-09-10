package com.desafio.superheroes.service;

import com.desafio.superheroes.dto.HeroiRequestDTO;
import com.desafio.superheroes.dto.HeroiResponseDTO;
import com.desafio.superheroes.entity.Heroi;
import com.desafio.superheroes.entity.HeroiSuperpoder;
import com.desafio.superheroes.entity.Superpoder;
import com.desafio.superheroes.exception.DuplicateHeroiNameException;
import com.desafio.superheroes.exception.HeroiNotFoundException;
import com.desafio.superheroes.repository.HeroiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HeroiServiceTest {

    @Mock
    private HeroiRepository heroiRepository;

    @Mock
    private SuperpoderService superpoderService;

    @InjectMocks
    private HeroiService heroiService;

    private HeroiRequestDTO heroiRequestDTO;
    private Heroi heroi;
    private Superpoder superpoder;

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

        heroi = new Heroi(
                "Clark Kent",
                "Superman",
                LocalDateTime.of(1938, 6, 1, 0, 0),
                1.91,
                102.0
        );
        heroi.setId(1);

        superpoder = new Superpoder("Super Força", "Capacidade de exercer força física muito além do normal humano");
        superpoder.setId(1);
    }

    @Test
    void deveCriarHeroiComSucesso() {
        // Arrange
        when(heroiRepository.existsByNomeHeroi(anyString())).thenReturn(false);
        when(superpoderService.todosIdsExistem(anyList())).thenReturn(true);
        when(superpoderService.buscarPorIds(anyList())).thenReturn(Arrays.asList(superpoder));
        when(heroiRepository.save(any(Heroi.class))).thenReturn(heroi);

        // Act
        HeroiResponseDTO resultado = heroiService.criar(heroiRequestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Superman", resultado.getNomeHeroi());
        assertEquals("Clark Kent", resultado.getNome());
        verify(heroiRepository, times(2)).save(any(Heroi.class));
    }

    @Test
    void deveLancarExcecaoQuandoNomeHeroiJaExiste() {
        // Arrange
        when(heroiRepository.existsByNomeHeroi(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateHeroiNameException.class, () -> {
            heroiService.criar(heroiRequestDTO);
        });

        verify(heroiRepository, never()).save(any(Heroi.class));
    }

    @Test
    void deveLancarExcecaoQuandoSuperpoderNaoExiste() {
        // Arrange
        when(heroiRepository.existsByNomeHeroi(anyString())).thenReturn(false);
        when(superpoderService.todosIdsExistem(anyList())).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            heroiService.criar(heroiRequestDTO);
        });

        verify(heroiRepository, never()).save(any(Heroi.class));
    }

    @Test
    void deveListarTodosOsHerois() {
        // Arrange
        List<Heroi> herois = Arrays.asList(heroi);
        when(heroiRepository.findAll()).thenReturn(herois);

        // Act
        List<HeroiResponseDTO> resultado = heroiService.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Superman", resultado.get(0).getNomeHeroi());
    }

    @Test
    void deveLancarExcecaoQuandoNenhumHeroiEncontrado() {
        // Arrange
        when(heroiRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(HeroiNotFoundException.class, () -> {
            heroiService.listarTodos();
        });
    }

    @Test
    void deveBuscarHeroiPorId() {
        // Arrange
        when(heroiRepository.findByIdWithSuperpoderes(anyInt())).thenReturn(Optional.of(heroi));

        // Act
        HeroiResponseDTO resultado = heroiService.buscarPorId(1);

        // Assert
        assertNotNull(resultado);
        assertEquals("Superman", resultado.getNomeHeroi());
        assertEquals(1, resultado.getId());
    }

    @Test
    void deveLancarExcecaoQuandoHeroiNaoEncontradoPorId() {
        // Arrange
        when(heroiRepository.findByIdWithSuperpoderes(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(HeroiNotFoundException.class, () -> {
            heroiService.buscarPorId(999);
        });
    }

    @Test
    void deveAtualizarHeroiComSucesso() {
        // Arrange
        Heroi heroiComSuperpoderes = new Heroi(
                "Clark Kent",
                "Superman",
                LocalDateTime.of(1938, 6, 1, 0, 0),
                1.91,
                102.0
        );
        heroiComSuperpoderes.setId(1);

        HeroiSuperpoder heroiSuperpoder = new HeroiSuperpoder();
        heroiSuperpoder.setHeroi(heroiComSuperpoderes);
        heroiSuperpoder.setSuperpoder(superpoder);
        heroiSuperpoder.setHeroiId(1);
        heroiSuperpoder.setSuperpoderId(1);
        heroiComSuperpoderes.getHeroiSuperpoderes().add(heroiSuperpoder);

        when(heroiRepository.findByIdWithSuperpoderes(anyInt())).thenReturn(Optional.of(heroiComSuperpoderes));
        when(superpoderService.todosIdsExistem(anyList())).thenReturn(true);
        when(superpoderService.buscarPorIds(anyList())).thenReturn(Arrays.asList(superpoder));
        when(heroiRepository.save(any(Heroi.class))).thenReturn(heroiComSuperpoderes);

        // Act
        HeroiResponseDTO resultado = heroiService.atualizar(1, heroiRequestDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("Superman", resultado.getNomeHeroi());
        assertEquals("Clark Kent", resultado.getNome());
        assertEquals(LocalDateTime.of(1938, 6, 1, 0, 0), resultado.getDataNascimento());
        assertEquals(1.91, resultado.getAltura());
        assertEquals(102.0, resultado.getPeso());
        assertNotNull(resultado.getSuperpoderes());
        assertEquals(1, resultado.getSuperpoderes().size());
        assertEquals("Super Força", resultado.getSuperpoderes().get(0).getSuperpoder());
        verify(heroiRepository).findByIdWithSuperpoderes(1);
        verify(superpoderService).todosIdsExistem(Arrays.asList(1, 2));
        verify(superpoderService).buscarPorIds(Arrays.asList(1, 2));
        verify(heroiRepository).save(any(Heroi.class));
    }

    @Test
    void deveDeletarHeroiComSucesso() {
        // Arrange
        when(heroiRepository.findById(anyInt())).thenReturn(Optional.of(heroi));
        doNothing().when(heroiRepository).delete(any(Heroi.class));

        // Act & Assert
        assertDoesNotThrow(() -> {
            heroiService.deletar(1);
        });

        verify(heroiRepository).delete(heroi);
    }

    @Test
    void deveLancarExcecaoQuandoTentarDeletarHeroiInexistente() {
        // Arrange
        when(heroiRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(HeroiNotFoundException.class, () -> {
            heroiService.deletar(999);
        });

        verify(heroiRepository, never()).delete(any(Heroi.class));
    }
}
