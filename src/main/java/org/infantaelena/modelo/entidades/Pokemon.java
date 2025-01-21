package org.infantaelena.modelo.entidades;

/**
 * Clase que representa a un pokemon
 * @author
 * @version 1.0
 * @date 24/04/2023
 *
 */
public class Pokemon {
    private String nombre;
    private String tipo;
    private int nivel;
    private int vida;
    private int ataque;

    public Pokemon(String nombre, String tipo, int nivel, int vida, int ataque) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = nivel;
        this.vida = vida;
        this.ataque = ataque;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public boolean equals(String n) {
        return this.nombre.equals(n);
    }

    public void set(String nombre, String tipo, int nivel, int vida, int ataque) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = nivel;
        this.vida = vida;
        this.ataque = ataque;
    }

    @Override
    public String toString() {
        return  "\nNombre: " + nombre +
                ", Tipo: " + tipo +
                ", Nivel: " + nivel +
                ", Vida: " + vida +
                ", Ataque: " + ataque + "\n";
    }
}
