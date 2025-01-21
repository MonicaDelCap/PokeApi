package org.infantaelena.excepciones;

public class PokemonNullFieldException extends Exception {
    public PokemonNullFieldException() {
        super("Hay campos nulos.");
    }
}
