package com.desafio.superheroes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class HeroiResponseDTO {

    private Integer id;
    private String nome;
    private String nomeHeroi;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataNascimento;
    private Double altura;
    private Double peso;
    private List<SuperpoderDTO> superpoderes;

    // Construtores
    public HeroiResponseDTO() {}

    public HeroiResponseDTO(Integer id, String nome, String nomeHeroi, LocalDateTime dataNascimento,
                            Double altura, Double peso, List<SuperpoderDTO> superpoderes) {
        this.id = id;
        this.nome = nome;
        this.nomeHeroi = nomeHeroi;
        this.dataNascimento = dataNascimento;
        this.altura = altura;
        this.peso = peso;
        this.superpoderes = superpoderes;
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

    public List<SuperpoderDTO> getSuperpoderes() {
        return superpoderes;
    }

    public void setSuperpoderes(List<SuperpoderDTO> superpoderes) {
        this.superpoderes = superpoderes;
    }
}
