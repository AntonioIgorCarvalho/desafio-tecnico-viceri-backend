package com.desafio.superheroes.exception;

public class DuplicateHeroiNameException extends RuntimeException {

    public DuplicateHeroiNameException(String nomeHeroi) {
        super("Já existe um herói com o nome '" + nomeHeroi + "'");
    }

}