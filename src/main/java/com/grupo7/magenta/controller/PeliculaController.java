package com.grupo7.magenta.controller;

import com.grupo7.magenta.dao.PeliculaDAO;
import com.grupo7.magenta.models.Pelicula;
import com.grupo7.magenta.models.PeliculaException;
import java.util.List;
import javax.swing.JOptionPane;

public class PeliculaController {
    private PeliculaDAO peliculaDAO;

    public PeliculaController() {
        this.peliculaDAO = new PeliculaDAO();
    }

    // Validar campos vacíos y tipos
    private void validarCampos(String titulo, String director, String anioStr, String duracionStr, String genero) throws PeliculaException {
        StringBuilder errores = new StringBuilder();
        if (titulo == null || titulo.trim().isEmpty()) errores.append("- Título obligatorio\n");
        if (director == null || director.trim().isEmpty()) errores.append("- Director obligatorio\n");
        if (anioStr == null || anioStr.trim().isEmpty()) errores.append("- Año obligatorio\n");
        if (duracionStr == null || duracionStr.trim().isEmpty()) errores.append("- Duración obligatoria\n");
        if (genero == null || genero.equals("Selecciona") || genero.trim().isEmpty()) errores.append("- Género obligatorio\n");
        try {
            Integer.parseInt(anioStr);
        } catch (Exception e) {
            errores.append("- Año debe ser numérico\n");
        }
        try {
            Integer.parseInt(duracionStr);
        } catch (Exception e) {
            errores.append("- Duración debe ser numérica\n");
        }
        if (errores.length() > 0) throw new PeliculaException(errores.toString());
    }

    // Agregar película
    public void agregarPelicula(String titulo, String director, String anioStr, String duracionStr, String genero) throws PeliculaException {
        validarCampos(titulo, director, anioStr, duracionStr, genero);
        Pelicula pelicula = new Pelicula(titulo, director, Integer.parseInt(anioStr), Integer.parseInt(duracionStr), genero);
        peliculaDAO.insertarPelicula(pelicula);
    }

    // Actualizar película
    public boolean actualizarPelicula(int id, String titulo, String director, String anioStr, String duracionStr, String genero) {
        try {
            validarCampos(titulo, director, anioStr, duracionStr, genero);
            Pelicula pelicula = new Pelicula(id, titulo, director, Integer.parseInt(anioStr), Integer.parseInt(duracionStr), genero);
            peliculaDAO.actualizarPelicula(pelicula);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Eliminar película
    public void eliminarPelicula(int id) throws PeliculaException {
        peliculaDAO.eliminarPelicula(id);
    }

    // Listar películas
    public List<Pelicula> listarPeliculas() throws PeliculaException {
        return peliculaDAO.listarPeliculas();
    }

    public Pelicula buscarPelicula(String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            return peliculaDAO.buscarPelicula(id);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean guardarPelicula(String idStr, String titulo, String director, String anioStr, String duracionStr, String genero) {
        try {
            // Validar campos
            validarCampos(titulo, director, anioStr, duracionStr, genero);
            // Si el id está vacío, inserta sin id (autonumérico)
            if (idStr == null || idStr.trim().isEmpty()) {
                Pelicula pelicula = new Pelicula(titulo, director, Integer.parseInt(anioStr), Integer.parseInt(duracionStr), genero);
                peliculaDAO.insertarPelicula(pelicula);
                JOptionPane.showMessageDialog(null, "Película guardada exitosamente.", "Guardado", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                int id = Integer.parseInt(idStr);
                if (peliculaDAO.existeId(id)) {
                    int opcion = JOptionPane.showConfirmDialog(null,
                        "El ID ya existe. No se puede duplicar.\n¿Deseas actualizar el registro?",
                        "ID duplicado",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                    if (opcion == JOptionPane.YES_OPTION) {
                        boolean actualizado = actualizarPelicula(id, titulo, director, anioStr, duracionStr, genero);
                        if (actualizado) {
                            JOptionPane.showMessageDialog(null, "Registro actualizado completamente.", "Actualización", JOptionPane.INFORMATION_MESSAGE);
                        }
                        return actualizado;
                    }
                    return false;
                }
                // Inserta con id específico (si la tabla lo permite)
                Pelicula pelicula = new Pelicula(id, titulo, director, Integer.parseInt(anioStr), Integer.parseInt(duracionStr), genero);
                peliculaDAO.insertarPelicula(pelicula);
                JOptionPane.showMessageDialog(null, "Película guardada exitosamente.", "Guardado", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
