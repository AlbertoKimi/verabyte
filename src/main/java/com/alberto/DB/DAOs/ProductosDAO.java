package com.alberto.DB.DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.alberto.DB.Conexion;
import com.alberto.Model.Producto;

public class ProductosDAO {

    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection con = Conexion.getConexion();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("IdProducto"),
                        rs.getString("IdCategoria"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getDouble("Precio"),
                        rs.getString("Marca"),
                        rs.getString("Imagen")));
            }

        } catch (SQLException e) {
            System.err.println("ERROR DETECTADO EN DAO: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error en la base de datos: " + e.getMessage(), e);
        }
        return lista;
    }

    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM productos WHERE IdProducto = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Producto(
                            rs.getInt("IdProducto"),
                            rs.getString("IdCategoria"),
                            rs.getString("Nombre"),
                            rs.getString("Descripcion"),
                            rs.getDouble("Precio"),
                            rs.getString("Marca"),
                            rs.getString("Imagen")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR DETECTADO EN DAO (obtenerPorId): " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al obtener producto por ID: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Producto> filtrarPorCategoria(List<Producto> productos, String idCategoria) {
        if (idCategoria == null || idCategoria.trim().isEmpty()) {
            return productos;
        }
        List<Producto> filtrados = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getIdCategoria() != null && p.getIdCategoria().equals(idCategoria)) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

    public List<Producto> filtrarPorMarca(List<Producto> productos, String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            return productos;
        }
        List<Producto> filtrados = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getMarca() != null && p.getMarca().equalsIgnoreCase(marca)) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

    public List<Producto> filtrarPorPrecio(List<Producto> productos, Double precioMin, Double precioMax) {
        if (precioMin == null && precioMax == null) {
            return productos;
        }
        List<Producto> filtrados = new ArrayList<>();
        for (Producto p : productos) {
            boolean cumpleMin = (precioMin == null) || (p.getPrecio() >= precioMin);
            boolean cumpleMax = (precioMax == null) || (p.getPrecio() <= precioMax);
            if (cumpleMin && cumpleMax) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

    public List<Producto> filtrarPorNombre(List<Producto> productos, String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return productos;
        }
        List<Producto> filtrados = new ArrayList<>();
        String lowerName = nombre.toLowerCase();
        for (Producto p : productos) {
            if (p.getNombre() != null && p.getNombre().toLowerCase().contains(lowerName)) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

    public List<String> obtenerMarcas() {
        List<String> marcas = new ArrayList<>();
        String sql = "SELECT DISTINCT Marca FROM productos ORDER BY Marca";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                marcas.add(rs.getString("Marca"));
            }

        } catch (SQLException e) {
            System.err.println("ERROR DETECTADO EN DAO: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error en la base de datos (obtenerMarcas): " + e.getMessage(), e);
        }
        return marcas;
    }
}
