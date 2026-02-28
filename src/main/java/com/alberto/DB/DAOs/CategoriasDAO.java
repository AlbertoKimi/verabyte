package com.alberto.DB.DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.alberto.DB.Conexion;
import com.alberto.Model.Categoria;

public class CategoriasDAO {

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
