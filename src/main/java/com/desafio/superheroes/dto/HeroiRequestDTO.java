package com.desafio.superheroes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

public class HeroiRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Nome do herói é obrigatório")
    private String nomeHeroi;

    @NotNull(message = "Data de nascimento é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataNascimento;

    @NotNull(message = "Altura é obrigatória")
    @Positive(message = "Altura deve ser um valor positivo")
    private Double altura;

    @NotNull(message = "Peso é obrigatório")
    @Positive(message = "Peso deve ser um valor positivo")
    private Double peso;

    @NotEmpty(message = "Pelo menos um superpoder deve ser selecionado")
    private List<Integer> superpoderesIds;

    // Construtores
    public HeroiRequestDTO() {}

    public HeroiRequestDTO(String nome, String nomeHeroi, LocalDateTime dataNascimento,
                           Double altura, Double peso, List<Integer> superpoderesIds) {
        this.nome = nome;
        this.nomeHeroi = nomeHeroi;
        this.dataNascimento = dataNascimento;
        this.altura = altura;
        this.peso = peso;
        this.superpoderesIds = superpoderesIds;
    }

    // Getters e Setters
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

    public List<Integer> getSuperpoderesIds() {
        return superpoderesIds;
    }

    public void setSuperpoderesIds(List<Integer> superpoderesIds) {
        this.superpoderesIds = superpoderesIds;
    }
}