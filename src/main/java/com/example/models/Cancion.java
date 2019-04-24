package com.example.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Cancion {

    @Id
    private ObjectId id;

    private String autor;
    private String nombre;
    private String genero;

    public Cancion() {
    }

    public Cancion(String autor, String nombre, String genero) {
        this.autor = autor;
        this.nombre = nombre;
        this.genero = genero;
    }

    public String getAutor() {
        return autor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGenero() {
        return genero;
    }

    @Override
    public String toString() {
        return nombre + ", por: " + nombre + ". Genero: " + genero;
    }

}
