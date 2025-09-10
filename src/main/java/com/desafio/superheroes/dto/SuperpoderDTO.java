package com.desafio.superheroes.dto;

public class SuperpoderDTO {

    private Integer id;
    private String superpoder;
    private String descricao;

    // Construtores
    public SuperpoderDTO() {}

    public SuperpoderDTO(Integer id, String superpoder, String descricao) {
        this.id = id;
        this.superpoder = superpoder;
        this.descricao = descricao;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSuperpoder() {
        return superpoder;
    }

    public void setSuperpoder(String superpoder) {
        this.superpoder = superpoder;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
