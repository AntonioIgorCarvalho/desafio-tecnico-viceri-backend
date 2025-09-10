package com.desafio.superheroes.config;

import com.desafio.superheroes.entity.Heroi;
import com.desafio.superheroes.entity.HeroiSuperpoder;
import com.desafio.superheroes.entity.Superpoder;
import com.desafio.superheroes.repository.HeroiRepository;
import com.desafio.superheroes.repository.SuperpoderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private HeroiRepository heroiRepository;

    @Autowired
    private SuperpoderRepository superpoderRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verificar se já existem heróis cadastrados
        if (heroiRepository.count() == 0) {
            criarHeroisExemplo();
        }
    }

    private void criarHeroisExemplo() {
        // Buscar alguns superpoderes para associar aos heróis
        List<Superpoder> todosSuperpoderes = superpoderRepository.findAll();

        if (todosSuperpoderes.size() >= 4) {
            // Criar Superman
            Heroi superman = new Heroi(
                    "Clark Kent",
                    "Superman",
                    LocalDateTime.of(1938, 6, 1, 0, 0),
                    1.91,
                    102.0
            );
            superman = heroiRepository.save(superman);

            // Adicionar superpoderes ao Superman
            adicionarSuperpoder(superman, todosSuperpoderes.get(0)); // Super Força
            adicionarSuperpoder(superman, todosSuperpoderes.get(1)); // Voo
            adicionarSuperpoder(superman, todosSuperpoderes.get(2)); // Visão de Raio-X

            heroiRepository.save(superman);

            // Criar Spider-Man
            Heroi spiderMan = new Heroi(
                    "Peter Parker",
                    "Spider-Man",
                    LocalDateTime.of(1962, 8, 1, 0, 0),
                    1.78,
                    76.0
            );
            spiderMan = heroiRepository.save(spiderMan);

            // Adicionar superpoderes ao Spider-Man
            adicionarSuperpoder(spiderMan, todosSuperpoderes.get(0)); // Super Força
            adicionarSuperpoder(spiderMan, todosSuperpoderes.get(3)); // Super Velocidade
            adicionarSuperpoder(spiderMan, todosSuperpoderes.get(9)); // Teia

            heroiRepository.save(spiderMan);
        }
    }

    private void adicionarSuperpoder(Heroi heroi, Superpoder superpoder) {
        HeroiSuperpoder heroiSuperpoder = new HeroiSuperpoder();
        heroiSuperpoder.setHeroi(heroi);
        heroiSuperpoder.setSuperpoder(superpoder);
        heroiSuperpoder.setHeroiId(heroi.getId());
        heroiSuperpoder.setSuperpoderId(superpoder.getId());
        heroi.getHeroiSuperpoderes().add(heroiSuperpoder);
    }
}