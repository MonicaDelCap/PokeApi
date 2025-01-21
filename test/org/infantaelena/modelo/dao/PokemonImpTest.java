package org.infantaelena.modelo.dao;

import org.infantaelena.excepciones.PokemonNotFoundException;
import org.infantaelena.excepciones.PokemonRepeatedException;
import org.infantaelena.modelo.entidades.Pokemon;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokemonImpTest {

    private static final PokemonDAOImp pokemonDAOImp;

    static {
        try {
            pokemonDAOImp = new PokemonDAOImp("pokemons.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    PokemonImpTest() throws FileNotFoundException {
    }

    @org.junit.jupiter.api.Test
    void crear() throws FileNotFoundException, PokemonRepeatedException {
        // después de usar el método crear(), si el tamaño
        // de la lista es igual al anterior tamaño+1, el metodo funciona correctamente
        int cantidadPokemons = pokemonDAOImp.leerTodos().size();
        Pokemon pokemon = new Pokemon("PokemonACrear", "test", 1, 1, 1);
        pokemonDAOImp.crear(pokemon);
        int cantidadPokemonsDespues = pokemonDAOImp.leerTodos().size();

        assertEquals(cantidadPokemons, cantidadPokemonsDespues - 1);
    }

    @org.junit.jupiter.api.Test
    void leerPorNombre() throws FileNotFoundException, PokemonRepeatedException, PokemonNotFoundException {
        // si lo que devuelve leerPorNombre(pokemonCreado) es el pokemon que se acaba de crear, funciona correctamente.
        Pokemon pokemon = new Pokemon("PokemonInventadoTest", "test", 5, 5, 5);
        pokemonDAOImp.crear(pokemon);
        assertTrue(pokemonDAOImp.leerPorNombre(pokemon.getNombre()).equals(pokemon.getNombre()));
    }

    @org.junit.jupiter.api.Test
    void leerTodos() throws FileNotFoundException {
        // si la cantidad de lineas del fichero es igual al tamaño de la lista
        // que devuelve el metodo leerTodos, el metodo funciona correctamente
        assertTrue(pokemonDAOImp.contarRegistrosFichero(pokemonDAOImp.getFileName()) == pokemonDAOImp.leerTodos().size());
    }

    @org.junit.jupiter.api.Test
    void actualizar() throws FileNotFoundException, PokemonRepeatedException, PokemonNotFoundException {
        Pokemon pokemon = new Pokemon("PokemonUpdate", "test", 1, 1, 1);
        pokemonDAOImp.crear(pokemon);
        Assertions.assertDoesNotThrow(() ->pokemonDAOImp.actualizar(pokemon));
    }

    @org.junit.jupiter.api.Test
    void eliminarPorNombre() throws Exception {
        // después de usar el método crear(), si el tamaño
        // de la lista es igual al anterior tamaño-1, el metodo funciona correctamente

        Pokemon pokemon = new Pokemon("PokemonAEliminar", "test", 1, 1, 1);
        pokemonDAOImp.crear(pokemon);

        int cantidadPokemons = pokemonDAOImp.leerTodos().size();

        pokemonDAOImp.eliminarPorNombre(pokemon.getNombre());

        int cantidadPokemonsDespues = pokemonDAOImp.leerTodos().size();

        assertEquals(cantidadPokemons, cantidadPokemonsDespues + 1);
    }

    @AfterAll
    static void cleanup() throws FileNotFoundException, PokemonNotFoundException {
        // Eliminar todos los Pokémon creados durante las pruebas
        pokemonDAOImp.eliminarPorNombre("PokemonUpdate");
        pokemonDAOImp.eliminarPorNombre("PokemonInventadoTest");
        pokemonDAOImp.eliminarPorNombre("PokemonACrear");
    }
}