package com.desafio.superheroes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "superpoderes")
public class Superpoder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "superpoder", length = 50, nullable = false)
    @NotBlank(message = "Nome do superpoder é obrigatório")
    private String superpoder;

    @Column(name = "descricao", length = 250, nullable = false)
    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @OneToMany(mappedBy = "superpoder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HeroiSuperpoder> heroiSuperpoderes = new ArrayList<>();

    // Construtores
    public Superpoder() {}

    public Superpoder(String superpoder, String descricao) {
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

    public List<HeroiSuperpoder> getHeroiSuperpoderes() {
        return heroiSuperpoderes;
    }

    public void setHeroiSuperpoderes(List<HeroiSuperpoder> heroiSuperpoderes) {
        this.heroiSuperpoderes = heroiSuperpoderes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Superpoder that = (Superpoder) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}