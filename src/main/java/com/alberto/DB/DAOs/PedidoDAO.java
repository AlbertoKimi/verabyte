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

public class PedidoDAO {
    
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
