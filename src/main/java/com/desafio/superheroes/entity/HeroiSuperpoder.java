package com.desafio.superheroes.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "herois_superpoderes")
@IdClass(HeroiSuperpoderId.class)
public class HeroiSuperpoder {

    @Id
    @Column(name = "heroi_id")
    private Integer heroiId;

    @Id
    @Column(name = "superpoder_id")
    private Integer superpoderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heroi_id", insertable = false, updatable = false)
    private Heroi heroi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "superpoder_id", insertable = false, updatable = false)
    private Superpoder superpoder;

    // Construtores
    public HeroiSuperpoder() {}

    public HeroiSuperpoder(Heroi heroi, Superpoder superpoder) {
        this.heroi = heroi;
        this.superpoder = superpoder;
        this.heroiId = heroi.getId();
        this.superpoderId = superpoder.getId();
    }

    // Getters e Setters
    public Integer getHeroiId() {
        return heroiId;
    }

    public void setHeroiId(Integer heroiId) {
        this.heroiId = heroiId;
    }

    public Integer getSuperpoderId() {
        return superpoderId;
    }

    public void setSuperpoderId(Integer superpoderId) {
        this.superpoderId = superpoderId;
    }

    public Heroi getHeroi() {
        return heroi;
    }

    public void setHeroi(Heroi heroi) {
        this.heroi = heroi;
        if (heroi != null) {
            this.heroiId = heroi.getId();
        }
    }

    public Superpoder getSuperpoder() {
        return superpoder;
    }

    public void setSuperpoder(Superpoder superpoder) {
        this.superpoder = superpoder;
        if (superpoder != null) {
            this.superpoderId = superpoder.getId();
        }
    }
}