package com.desafio.superheroes.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "herois")
public class Heroi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 120, nullable = false)
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Column(name = "nome_heroi", length = 120, nullable = false, unique = true)
    @NotBlank(message = "Nome do herói é obrigatório")
    private String nomeHeroi;

    @Column(name = "data_nascimento", nullable = false)
    @NotNull(message = "Data de nascimento é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataNascimento;

    @Column(name = "altura", nullable = false)
    @NotNull(message = "Altura é obrigatória")
    @Positive(message = "Altura deve ser um valor positivo")
    private Double altura;

    @Column(name = "peso", nullable = false)
    @NotNull(message = "Peso é obrigatório")
    @Positive(message = "Peso deve ser um valor positivo")
    private Double peso;

    @OneToMany(mappedBy = "heroi", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HeroiSuperpoder> heroiSuperpoderes = new ArrayList<>();

    // Construtores
    public Heroi() {}

    public Heroi(String nome, String nomeHeroi, LocalDateTime dataNascimento, Double altura, Double peso) {
        this.nome = nome;
        this.nomeHeroi = nomeHeroi;
        this.dataNascimento = dataNascimento;
        this.altura = altura;
        this.peso = peso;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeHeroi() {
        return nomeHeroi;
    }

    public void setNomeHeroi(String nomeHeroi) {
        this.nomeHeroi = nomeHeroi;
    }

    public LocalDateTime getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDateTime dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public List<HeroiSuperpoder> getHeroiSuperpoderes() {
        return heroiSuperpoderes;
    }

    public void setHeroiSuperpoderes(List<HeroiSuperpoder> heroiSuperpoderes) {
        this.heroiSuperpoderes = heroiSuperpoderes;
    }

    // Métodos auxiliares
    public void addSuperpoder(Superpoder superpoder) {
        HeroiSuperpoder heroiSuperpoder = new HeroiSuperpoder();
        heroiSuperpoder.setHeroi(this);
        heroiSuperpoder.setSuperpoder(superpoder);
        this.heroiSuperpoderes.add(heroiSuperpoder);
    }

    public void removeSuperpoder(Superpoder superpoder) {
        this.heroiSuperpoderes.removeIf(hs -> hs.getSuperpoder().equals(superpoder));
    }
}