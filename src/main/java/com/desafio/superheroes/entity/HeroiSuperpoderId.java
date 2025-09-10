package com.desafio.superheroes.entity;

import java.io.Serializable;
import java.util.Objects;

public class HeroiSuperpoderId implements Serializable {

    private Integer heroiId;
    private Integer superpoderId;

    public HeroiSuperpoderId() {}

    public HeroiSuperpoderId(Integer heroiId, Integer superpoderId) {
        this.heroiId = heroiId;
        this.superpoderId = superpoderId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeroiSuperpoderId that = (HeroiSuperpoderId) o;
        return Objects.equals(heroiId, that.heroiId) && Objects.equals(superpoderId, that.superpoderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heroiId, superpoderId);
    }
}