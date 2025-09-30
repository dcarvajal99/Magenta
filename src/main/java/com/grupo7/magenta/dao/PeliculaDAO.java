package com.grupo7.magenta.dao;

import com.grupo7.magenta.models.Pelicula;
import com.grupo7.magenta.models.PeliculaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    // Método para verificar si existe un ID
    public boolean existeId(int id) throws PeliculaException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnector.getConnection();
            String sql = "SELECT COUNT(*) FROM Cartelera WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new PeliculaException("Error al verificar el ID: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, con);
        }
        return false;
    }

    // Método para insertar una película
    public void insertarPelicula(Pelicula pelicula) throws PeliculaException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getConnection();
            String sql = "INSERT INTO Cartelera (titulo, director, anio, duracion, genero) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getDirector());
            ps.setInt(3, pelicula.getAnio());
            ps.setInt(4, pelicula.getDuracion());
            ps.setString(5, pelicula.getGenero());
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new PeliculaException("No se pudo insertar la película.");
            }
        } catch (SQLException e) {
            throw new PeliculaException("Error al insertar película: " + e.getMessage());
        } finally {
            cerrarRecursos(null, ps, con);
        }
    }

    // Método para listar todas las películas
    public List<Pelicula> listarPeliculas() throws PeliculaException {
        List<Pelicula> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnector.getConnection();
            String sql = "SELECT * FROM Cartelera";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pelicula p = new Pelicula(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("director"),
                    rs.getInt("anio"),
                    rs.getInt("duracion"),
                    rs.getString("genero")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new PeliculaException("Error al listar películas: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, con);
        }
        return lista;
    }

    // Método para eliminar una película
    public void eliminarPelicula(int id) throws PeliculaException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getConnection();
            String sql = "DELETE FROM Cartelera WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new PeliculaException("No se encontró la película para eliminar.");
            }
        } catch (SQLException e) {
            throw new PeliculaException("Error al eliminar película: " + e.getMessage());
        } finally {
            cerrarRecursos(null, ps, con);
        }
    }

    // Método para actualizar una película
    public void actualizarPelicula(Pelicula pelicula) throws PeliculaException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnector.getConnection();
            String sql = "UPDATE Cartelera SET titulo=?, director=?, anio=?, duracion=?, genero=? WHERE id=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getDirector());
            ps.setInt(3, pelicula.getAnio());
            ps.setInt(4, pelicula.getDuracion());
            ps.setString(5, pelicula.getGenero());
            ps.setInt(6, pelicula.getId());
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new PeliculaException("No se encontró la película para actualizar.");
            }
        } catch (SQLException e) {
            throw new PeliculaException("Error al actualizar película: " + e.getMessage());
        } finally {
            cerrarRecursos(null, ps, con);
        }
    }

    // Método para buscar una película por su ID
    public Pelicula buscarPelicula(int id) throws PeliculaException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnector.getConnection();
            String sql = "SELECT * FROM Cartelera WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Pelicula(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("director"),
                    rs.getInt("anio"),
                    rs.getInt("duracion"),
                    rs.getString("genero")
                );
            }
        } catch (SQLException e) {
            throw new PeliculaException("Error al buscar película: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, con);
        }
        return null;
    }

    // Método auxiliar para cerrar recursos
    private void cerrarRecursos(PreparedStatement ps, Connection con) {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Método auxiliar para cerrar recursos con ResultSet
    private void cerrarRecursos(ResultSet rs, PreparedStatement ps, Connection con) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
