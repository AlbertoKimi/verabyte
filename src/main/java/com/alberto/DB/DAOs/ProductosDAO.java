package com.alberto.DB.DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.alberto.DB.Conexion;
import com.alberto.Model.Producto;

/**
 * Clase de Acceso a Datos (DAO) para la gestión del catálogo de Productos.
 * Proporciona métodos para listar, buscar por ID y diversos filtros 
 * (categoría, marca, precio, nombre).
 */
public class ProductosDAO {

    /**
     * Recupera todos los productos almacenados en la base de datos sin ningún filtro.
     *
     * @return Una lista constante de objetos {@link Producto}.
     * @throws RuntimeException En caso de error de SQL.
     */
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

    /**
     * Busca y recupera un producto específico basado en su identificador único.
     *
     * @param id El identificador del producto a buscar.
     * @return El objeto {@link Producto} si se encuentra, de lo contrario null.
     * @throws RuntimeException En caso de un problema con la consulta SQL.
     */
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

    /**
     * Filtra una lista existente de productos devolviendo solo aquellos que correspondan
     * a la categoría indicada. Este método trabaja en memoria.
     *
     * @param productos   La lista original de productos a filtrar.
     * @param idCategoria El ID de la categoría por la que filtrar. Si es nulo o vacío, devuelve la lista original.
     * @return Una nueva lista de productos filtrados.
     */
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

    /**
     * Filtra una lista existente de productos devolviendo solo los de una marca específica.
     * Trabajo en memoria. No distingue entre mayúsculas y minúsculas (IgnoreCase).
     *
     * @param productos La lista de productos a filtrar.
     * @param marca     El nombre de la marca. Si es nulo o vacío, devuelve la lista intacta.
     * @return Una nueva lista de productos filtrada por la marca indicada.
     */
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

    /**
     * Filtra los productos que se encuentren dentro de un rango de precios.
     * Trabajo en memoria.
     *
     * @param productos La lista de productos base.
     * @param precioMin El precio mínimo a aceptar (o null si no hay límite inferior).
     * @param precioMax El precio máximo a aceptar (o null si no hay límite superior).
     * @return La lista de productos cuyos precios coincidan con el rango especificado.
     */
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

    /**
     * Filtra en memoria los productos cuyo nombre contenga el texto buscado.
     * Ignora mayúsculas o minúsculas asumiendo todo en minúsculas.
     *
     * @param productos La lista a filtrar.
     * @param nombre    La cadena de texto a buscar dentro del nombre del producto.
     * @return Lista de productos filtrados. Si el texto es nulo o vacío, devuelve el listado completo.
     */
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

    /**
     * Recupera todas las marcas diferentes registradas en la tabla de productos,
     * ordenadas alfabéticamente. Útil para filtros de interfaz.
     *
     * @return Lista de strings que representan cada marca de la base de datos.
     * @throws RuntimeException si falla la consulta (SELECT DISTINCT).
     */
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
