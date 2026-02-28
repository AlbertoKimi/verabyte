package com.alberto.DB.DAOs;

import com.alberto.DB.Conexion;
import com.alberto.DTO.ItemCarritoDTO;
import com.alberto.DTO.PedidoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de Acceso a Datos (DAO) para la gestión de los Pedidos.
 * Permite listar los pedidos de un usuario y obtener los detalles (líneas) de un pedido concreto.
 */
public class PedidoDAO {
    
    /**
     * Enumera todos los pedidos realizados por un usuario específico,
     * ordenados por fecha y por ID de forma descendente (los más recientes primero).
     *
     * @param idUsuario El ID del usuario del que se quieren obtener los pedidos.
     * @return Una lista de objetos {@link PedidoDTO}.
     */
    public List<PedidoDTO> listarPorUsuario(int idUsuario) {
        List<PedidoDTO> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE IdUsuario = ? ORDER BY Fecha DESC, IdPedido DESC";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PedidoDTO p = new PedidoDTO();
                    p.setIdPedido(rs.getInt("IdPedido"));
                    p.setFecha(rs.getDate("Fecha"));
                    p.setEstado(rs.getString("Estado"));
                    p.setIdUsuario(rs.getInt("IdUsuario"));
                    p.setImporte(rs.getDouble("Importe"));
                    p.setIva(rs.getDouble("Iva"));
                    pedidos.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return pedidos;
    }
    
    /**
     * Obtiene todas las líneas (ítems) asociadas a un pedido determinado.
     * Realiza un JOIN con la tabla de productos para incluir información detallada
     * como el nombre, precio unitario e imagen de cada producto en el momento del pedido.
     *
     * @param idPedido El ID del pedido del que se quieren obtener los detalles.
     * @return Una lista de objetos {@link ItemCarritoDTO} simulando las líneas del pedido.
     */
    public List<ItemCarritoDTO> obtenerLineasPedido(int idPedido) {
        List<ItemCarritoDTO> lineas = new ArrayList<>();
        String sql = "SELECT lp.IdProducto, p.Nombre, p.Precio, lp.Cantidad, p.Imagen " +
                     "FROM lineaspedidos lp " +
                     "JOIN productos p ON lp.IdProducto = p.IdProducto " +
                     "WHERE lp.IdPedido = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idPedido);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemCarritoDTO item = new ItemCarritoDTO(
                            rs.getInt("IdProducto"),
                            rs.getString("Nombre"),
                            rs.getDouble("Precio"),
                            rs.getInt("Cantidad"),
                            rs.getString("Imagen")
                    );
                    lineas.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lineas;
    }
}
