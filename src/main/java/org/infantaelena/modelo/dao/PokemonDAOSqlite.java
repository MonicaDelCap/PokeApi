package org.infantaelena.modelo.dao;

import org.infantaelena.excepciones.PokemonNotFoundException;
import org.infantaelena.excepciones.PokemonRepeatedException;
import org.infantaelena.modelo.entidades.Pokemon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAOSqlite implements PokemonDAO {

    private Connection connection;


    public PokemonDAOSqlite() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:bdd/Pokemonbdd.db");
            crearTabla();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void crearTabla() throws SQLException {
        try (Statement sntc = connection.createStatement()) {
            sntc.executeUpdate("CREATE TABLE if not exists pokemon (\n" +
                    "    nombre TEXT PRIMARY KEY,\n" +
                    "    tipo TEXT,\n" +
                    "    nivel INTEGER,\n" +
                    "    vida INTEGER,\n" +
                    "    ataque INTEGER\n" +
                    ")");
            System.out.println("Tabla creada con éxito");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla");
        }
    }
    @Override
    public void crear(Pokemon pokemon) throws PokemonRepeatedException {
        try (Statement sntc = connection.createStatement()) {
            sntc.executeUpdate("INSERT INTO pokemon (nombre, tipo, nivel, vida, ataque) \n" + "VALUES ('"+pokemon.getNombre()+"', '"+pokemon.getTipo()+"', '"+pokemon.getNivel()+"', '"+pokemon.getVida()+"', '"+pokemon.getAtaque()+"')");
        } catch (SQLException e) {
            throw new PokemonRepeatedException();
        }
    }


    @Override
    public Pokemon leerPorNombre(String nombre) throws PokemonNotFoundException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM pokemon WHERE nombre = ?")) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Pokemon(rs.getString("nombre"), rs.getString("tipo"), rs.getInt("nivel"), rs.getInt("vida"), rs.getInt("ataque"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        throw new PokemonNotFoundException();
    }

    @Override
    public List<Pokemon> leerPorTipo(String tipo) {
        List<Pokemon> pokemons = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM pokemon WHERE tipo = ?")) {
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pokemons.add(new Pokemon(rs.getString("nombre"), rs.getString("tipo"), rs.getInt("nivel"), rs.getInt("vida"), rs.getInt("ataque")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pokemons;
    }


    @Override
    public List<Pokemon> leerTodos() {
        List<Pokemon> pokemons = new ArrayList<>();
        try (Statement stnc = connection.createStatement()) {
            ResultSet rs = stnc.executeQuery("select * from pokemon");
            while (rs.next()) {
                pokemons.add(new Pokemon(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error al listar personas "+e.getMessage());
        }

        return pokemons;
    }

    @Override
    public void actualizar(Pokemon pokemon) throws PokemonNotFoundException {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE pokemon SET tipo = ?, nivel = ?, vida = ?, ataque = ? WHERE nombre = ?")) {
            ps.setString(1, pokemon.getTipo());
            ps.setInt(2, pokemon.getNivel());
            ps.setInt(3, pokemon.getVida());
            ps.setInt(4, pokemon.getAtaque());
            ps.setString(5, pokemon.getNombre());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new PokemonNotFoundException();
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el Pokemon");
        }
    }

    @Override
    public void eliminarPorNombre(String nombre) throws PokemonNotFoundException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM pokemon WHERE nombre = ?")) {
            ps.setString(1, nombre);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new PokemonNotFoundException();
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el Pokemon");
        }
    }

}
