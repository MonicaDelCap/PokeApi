package org.infantaelena.controlador;

import org.infantaelena.excepciones.PokemonNotFoundException;
import org.infantaelena.excepciones.PokemonNullFieldException;
import org.infantaelena.excepciones.PokemonRepeatedException;
import org.infantaelena.modelo.dao.PokemonDAO;
import org.infantaelena.modelo.dao.PokemonDAOImp;
import org.infantaelena.modelo.dao.PokemonDAOSqlite;
import org.infantaelena.modelo.entidades.Pokemon;
import org.infantaelena.vista.Vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Clase que se encarga de obtener los datos de la vista
 * y enviarlos al modelo para que los procese
 *
 * @author
 * @version 1.0
 * @date 24/04/2023
 *
 */


public class Controlador  {

    private final Vista vista;
    //private PokemonDAOImp modelo;
    private PokemonDAOSqlite modelo;

    private List<Pokemon> pokemons;

    public Controlador() {
        vista = new Vista();
        try {
            modelo = new PokemonDAOSqlite();
            //modelo = new PokemonDAOImp("pokemons.csv");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        actualizarDatosInterfaz();

        vista.getAnadirButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crear();
            }
        });

        vista.getBuscarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBusqueda();
            }
        });

        vista.getEliminarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPokemon();
            }
        });

        vista.getModificarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPokemon();
            }
        });

        vista.getListarTodosButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarTodos();
            }
        });

        vista.getLimpiarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.limpiarCampos();
            }
        });


    }

    public void actualizarDatosInterfaz() {
        vista.limpiarTextArea();
        try {
            pokemons = new ArrayList<>(modelo.leerTodos());

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        //coge el contenido del fichero csv / bdd
        //lo escribe en la interfaz
        for (Pokemon pokemon: pokemons) {
            vista.getTextArea1().setText(vista.getTextArea1().getText()+pokemon.toString());
        }
    }


    public void crear() {
        // El metodo parseInt lanza NumberFormatException si hay un valor que no puede parsear como " " o "1f"
        try {
            modelo.crear(new Pokemon(vista.getTextField1().getText(), vista.getTextField2().getText(), Integer.parseInt(vista.getTextField3().getText()), Integer.parseInt(vista.getTextField4().getText()), Integer.parseInt(vista.getTextField5().getText())));
            if (vista.hayCamposNull()) {
                // realmente solo interesa saber si nombre o tipo son nulos
                // porque si alguno de los otros campo numericos ya lo es, parseInt
                // lanza la excepcion NumberFormatException
                throw new PokemonNullFieldException();
            }
            actualizarDatosInterfaz();
            vista.mostrarMensajeVentana("Pokemon añadido con éxito.");
            vista.limpiarCampos();
        } catch (PokemonRepeatedException ex) {
            vista.mostrarMensajeVentana(ex);
        } catch (NumberFormatException | PokemonNullFieldException ex) {
            // Por ese mismo motivo se hace este if, para ver por cual de las excepciones viene
            if (vista.hayCamposNull()) {
                vista.mostrarMensajeVentana("Hay campos nulos, por favor rellenalos.");
            } else {
                vista.mostrarMensajeVentana("No se puede introducir texto en un campo numérico.");
            }
        }
    }

    public void getBusqueda() {
        try {
            Pokemon pokemon;
            // si campo nombre y tipo estan vacios..
            if (vista.getTextField1().getText().equals("") && vista.getTextField2().getText().equals("")) {
                vista.mostrarMensajeVentana("Se debe buscar al menos por nombre o tipo.");
                actualizarDatosInterfaz();
                // si campo nombre está vacío, busca por tipo
            } else if (vista.getTextField1().getText().equals("")) {
                vista.getTextArea1().setText(modelo.leerPorTipo(vista.getTextField2().getText()).toString());
                // si campo tipo está vacío o campos nombre y tipo tienen datos, debe buscar por nombre
            } else if (vista.getTextField2().getText().equals("") || !(vista.getTextField1().getText().equals("") && vista.getTextField2().getText().equals(""))) {
                pokemon = modelo.leerPorNombre(vista.getTextField1().getText());
                vista.getTextArea1().setText(pokemon.toString());
                vista.rellenarCampos(pokemon);
            }
        } catch (PokemonNotFoundException ex) {
            vista.mostrarMensajeVentana(ex);
        }
    }

    public void eliminarPokemon() {
        try {
            String nombrePokemon = vista.getTextField1().getText();
            if (!vista.getTextField1().getText().equals("")) {
                if (vista.confirmarBorrado(nombrePokemon)){
                    modelo.eliminarPorNombre(vista.getTextField1().getText());
                    actualizarDatosInterfaz();
                    vista.mostrarMensajeVentana("Pokemon eliminado con éxito.");
                    vista.limpiarCampos();
                }
            } else {
                vista.mostrarMensajeVentana("Debes indicar el nombre del pokemon a borrar.");
            }
        } catch (PokemonNotFoundException ex) {
            vista.mostrarMensajeVentana(ex);
        }
    }

    public void actualizarPokemon() {
        try {
            Pokemon pokemon = new Pokemon(vista.getTextField1().getText(), vista.getTextField2().getText(), Integer.parseInt(vista.getTextField3().getText()), Integer.parseInt(vista.getTextField4().getText()), Integer.parseInt(vista.getTextField5().getText()));
            if (vista.getTextField1().getText().equals("") || vista.getTextField2().getText().equals("")) {
                throw new PokemonNullFieldException();
            }
            modelo.actualizar(pokemon);
            actualizarDatosInterfaz();
            vista.mostrarMensajeVentana("Pokemon modificado con éxito.");
            vista.limpiarCampos();
        } catch (PokemonNotFoundException ex) {
            vista.mostrarMensajeVentana(ex);
        } catch (NumberFormatException | PokemonNullFieldException ex) {
            if (vista.hayCamposNull()) {
                vista.mostrarMensajeVentana("Hay campos nulos, por favor rellénalos.");
            } else {
                vista.mostrarMensajeVentana("No se puede introducir texto en un campo numérico.");
            }
        }
    }

    public void listarTodos() {
        actualizarDatosInterfaz();
    }
    
}
