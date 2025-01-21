package org.infantaelena.modelo.dao;

import org.infantaelena.excepciones.PokemonNotFoundException;
import org.infantaelena.excepciones.PokemonRepeatedException;
import org.infantaelena.modelo.entidades.Pokemon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * Interfaz que define los métodos de acceso a datos de la clase Pokemon
 * @author
 * @version 1.0
 * @date 24/04/2023
 *
 */
public interface PokemonDAO {

    // Método para crear un nuevo Pokemon

    public void crear(Pokemon pokemon) throws PokemonRepeatedException, FileNotFoundException, SQLException;

    // Método para leer un Pokemon de la base de datos por su nombre

    public Pokemon leerPorNombre(String nombre) throws PokemonNotFoundException, IOException;
    public List<Pokemon> leerPorTipo(String tipo) throws FileNotFoundException;

    // Método para leer todos los Pokemons

    public List<Pokemon> leerTodos() throws FileNotFoundException, SQLException;

    // Método para actualizar un Pokemon en la base de datos

    public void actualizar(Pokemon pokemon) throws PokemonNotFoundException, IOException, PokemonRepeatedException;

    // Método para eliminar un Pokemon por su nombre

    public void eliminarPorNombre(String nombre) throws PokemonNotFoundException, IOException;

}