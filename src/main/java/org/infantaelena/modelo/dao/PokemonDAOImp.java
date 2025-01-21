package org.infantaelena.modelo.dao;

import org.infantaelena.excepciones.PokemonNotFoundException;
import org.infantaelena.excepciones.PokemonRepeatedException;
import org.infantaelena.modelo.entidades.Pokemon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa los métodos de acceso a datos de la clase Pokemon
 * Esta puede hacerse mediante un fichero CSV separado por puntos y coma o una base de datos
 *
 * @author
 * @version 1.0
 * @date 24/04/2023
 */
public class PokemonDAOImp implements PokemonDAO {
    private final String fileName;

    public PokemonDAOImp(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
    }

    @Override
    public void crear(Pokemon pokemon) throws PokemonRepeatedException {
        List<Pokemon> pokemons = new ArrayList<>(leerTodos());
        for (Pokemon p: pokemons) {
            if (p.getNombre().equals(pokemon.getNombre())) {
                throw new PokemonRepeatedException();
            }
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            out.println(pokemon.getNombre() + "," + pokemon.getTipo() + "," + pokemon.getNivel() + "," + pokemon.getVida() + "," + pokemon.getAtaque());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Pokemon leerPorNombre(String nombre) throws PokemonNotFoundException {
        //busqueda en fichero
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(nombre)) {
                    return new Pokemon(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]));
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());

        }
        throw new PokemonNotFoundException();

    }

    @Override
    public List<Pokemon> leerPorTipo(String tipo) {

        List<Pokemon> pokemonsConTipo = new ArrayList<>();
        /*
        List<Pokemon> pokemons = new ArrayList<>(leerTodos());
        for (Pokemon pokemon: pokemons) {
            if (pokemon.getTipo().equals(tipo)) {
                pokemonsConTipo.add(pokemon);
            }
        }
        return pokemonsConTipo;

        */
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            while (in.readLine() != null) {
                String filaPokemon = in.readLine();
                String[] pokemon = filaPokemon.split(",");
                if (pokemon[1].equals(tipo)) {
                    pokemonsConTipo.add(new Pokemon(pokemon[0], pokemon[1], Integer.parseInt(pokemon[2]), Integer.parseInt(pokemon[3]), Integer.parseInt(pokemon[4])));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pokemonsConTipo;


    }

    @Override
    public List<Pokemon> leerTodos() {
        List<Pokemon> pokemons = new ArrayList<>();
        String line;
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            while ((line = in.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != 5) {
                    continue;
                }
                Pokemon pokemon = new Pokemon(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]));
                pokemons.add(pokemon);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pokemons;
    }

    @Override
    public void actualizar(Pokemon pokemon) throws PokemonNotFoundException {
        List<Pokemon> pokemons = leerTodos();
        boolean encontro = false;
        int index = 0;
        for (Pokemon p: pokemons) {
            if (p.getNombre().equals(pokemon.getNombre())) {
                encontro = true;
                break;
            }
            index++;
        }
        if (!encontro) {
            throw new PokemonNotFoundException();
        }

        pokemon.set(pokemon.getNombre(), pokemon.getTipo(), pokemon.getNivel(), pokemon.getVida(), pokemon.getAtaque());
        pokemons.set(index, pokemon);

        limpiarFichero();
        enviarAFichero(pokemons);

    }

    @Override
    public void eliminarPorNombre(String nombre) throws PokemonNotFoundException {
        List<Pokemon> pokemons = new ArrayList<>(leerTodos());
        boolean encontro = false;
        for (Pokemon pokemon: pokemons) {
            if (pokemon.getNombre().equals(nombre)) {
                pokemons.remove(pokemon);
                encontro = true;
                break;
            }
        }
        if (!encontro) {
            throw new PokemonNotFoundException();
        }

        limpiarFichero();
        enviarAFichero(pokemons);

    }

    public void enviarAFichero(List<Pokemon> pokemons) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
            for (Pokemon pokemon : pokemons) {
                out.println(pokemon.getNombre() + "," + pokemon.getTipo() + "," + pokemon.getNivel() + "," + pokemon.getVida() + "," + pokemon.getAtaque());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void limpiarFichero() {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.write("");
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
    public String getFileName() {
        return fileName;
    }

    public int contarRegistrosFichero(String fileName) {
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage());
        }
        return lineCount;
    }

}
