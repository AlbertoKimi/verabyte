package com.alberto.DB.DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.alberto.DB.Conexion;
import com.alberto.Model.Categoria;

/**
 * Clase de Acceso a Datos (DAO) para la gestión de las Categorías de productos.
 * Contiene los métodos necesarios para interactuar con la tabla 'categorias'.
 */
public class CategoriasDAO {

    /**
     * Recupera todas las categorías almacenadas en la base de datos.
     *
     * @return Una lista de objetos {@link Categoria}. Si no hay categorías,
     *         devuelve una lista vacía.
     * @throws RuntimeException si ocurre un error de SQL durante la consulta.
     */
    public List<Categoria> listarTodas() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Categoria(
                        rs.getInt("IdCategoria"),
                        rs.getString("Nombre"),
                        rs.getString("Imagen")
                ));
            }

        } catch (SQLException e) {
            System.err.println("ERROR DETECTADO EN DAO: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error en la base de datos (CategoriasDAO): " + e.getMessage(), e);
        }
        return lista;
    }
}
