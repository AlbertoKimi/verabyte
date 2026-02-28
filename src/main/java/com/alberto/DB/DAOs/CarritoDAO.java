package com.alberto.DB.DAOs;

import com.alberto.DB.Conexion;
import com.alberto.DTO.CarritoDTO;
import com.alberto.DTO.ItemCarritoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CarritoDAO {

    public void guardarCarrito(int idUsuario, CarritoDTO carrito) {
        String sqlDelete = "DELETE FROM carrito WHERE id_usuario = ?";
        String sqlInsert = "INSERT INTO carrito (id_usuario, id_producto, cantidad) VALUES (?, ?, ?)";

        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false);

            try (PreparedStatement psDelete = con.prepareStatement(sqlDelete)) {
                psDelete.setInt(1, idUsuario);
                psDelete.executeUpdate();
            }

            if (carrito != null && !carrito.getItems().isEmpty()) {
                try (PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {
                    for (ItemCarritoDTO item : carrito.getItems()) {
                        psInsert.setInt(1, idUsuario);
                        psInsert.setInt(2, item.getIdProducto());
                        psInsert.setInt(3, item.getCantidad());
                        psInsert.addBatch();
                    }
                    psInsert.executeBatch();
                }
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public CarritoDTO cargarCarrito(int idUsuario) {
        CarritoDTO carrito = new CarritoDTO();
        String sql = "SELECT ci.id_producto, p.Nombre, p.Precio, ci.cantidad, p.Imagen " +
                     "FROM carrito ci " +
                     "JOIN productos p ON ci.id_producto = p.IdProducto " +
                     "WHERE ci.id_usuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemCarritoDTO item = new ItemCarritoDTO(
                            rs.getInt("id_producto"),
                            rs.getString("Nombre"),
                            rs.getDouble("Precio"),
                            rs.getInt("cantidad"),
                            rs.getString("Imagen")
                    );
                    carrito.addItem(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carrito;
    }

    public boolean finalizarCompra(int idUsuario, CarritoDTO carrito) {
        if (carrito == null || carrito.getItems().isEmpty()) {
            return false;
        }

        String sqlPedido = "INSERT INTO pedidos (Fecha, Estado, IdUsuario, Importe, Iva) VALUES (CURDATE(), 'f', ?, ?, ?)";
        String sqlLinea = "INSERT INTO lineaspedidos (IdPedido, IdProducto, Cantidad) VALUES (?, ?, ?)";
        String sqlDeleteCart = "DELETE FROM carrito WHERE id_usuario = ?";

        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false);

            int idPedido = -1;
            try (PreparedStatement psPedido = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                psPedido.setInt(1, idUsuario);
                psPedido.setDouble(2, carrito.getTotal());
                psPedido.setDouble(3, carrito.getTotal() * 0.21);
                psPedido.executeUpdate();

                try (ResultSet rs = psPedido.getGeneratedKeys()) {
                    if (rs.next()) {
                        idPedido = rs.getInt(1);
                    } else {
                        throw new SQLException("Error al obtener ID del pedido generado.");
                    }
                }
            }

            try (PreparedStatement psLinea = con.prepareStatement(sqlLinea)) {
                for (ItemCarritoDTO item : carrito.getItems()) {
                    psLinea.setInt(1, idPedido);
                    psLinea.setInt(2, item.getIdProducto());
                    psLinea.setInt(3, item.getCantidad());
                    psLinea.addBatch();
                }
                psLinea.executeBatch();
            }

            try (PreparedStatement psDeleteCart = con.prepareStatement(sqlDeleteCart)) {
                psDeleteCart.setInt(1, idUsuario);
                psDeleteCart.executeUpdate();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
