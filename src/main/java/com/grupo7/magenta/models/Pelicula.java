package com.grupo7.magenta.models;

public class Pelicula {
    private int id;
    private String titulo;
    private String director;
    private int anio;
    private int duracion;
    private String genero;

    // Constructor vacío
    public Pelicula() {
    }

    // Constructor con parámetros (sin ID para inserción)
    public Pelicula(String titulo, String director, int anio, int duracion, String genero) {
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
    }

    // Constructor completo (con ID para consultas)
    public Pelicula(int id, String titulo, String director, int anio, int duracion, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", director='" + director + '\'' +
                ", anio=" + anio +
                ", duracion=" + duracion +
                ", genero='" + genero + '\'' +
                '}';
    }
}
