package com.desafio.superheroes.exception;

public class HeroiNotFoundException extends RuntimeException {

    public HeroiNotFoundException(String message) {
        super(message);
    }

    public HeroiNotFoundException(Integer id) {
        super("Herói com ID " + id + " não encontrado");
    }
}