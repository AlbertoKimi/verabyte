package com.alberto.DB.DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.alberto.DB.Conexion;
import com.alberto.Model.Usuario;

public class UsuariosDAO {

    public Usuario findByEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE Email = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return crearUsuarioDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al buscar usuario por email: " + e.getMessage(), e);
        }
        return null;
    }

    public boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (Email, Password, Nombre, Apellidos, NIF, Telefono, Direccion, CodigoPostal, Localidad, Provincia, Avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, usuario.getEmail());
            pst.setString(2, usuario.getPassword());
            pst.setString(3, usuario.getNombre());
            pst.setString(4, usuario.getApellidos());
            pst.setString(5, usuario.getNif());
            pst.setString(6, usuario.getTelefono());
            pst.setString(7, usuario.getDireccion());
            pst.setString(8, usuario.getCodigoPostal());
            pst.setString(9, usuario.getLocalidad());
            pst.setString(10, usuario.getProvincia());
            pst.setString(11, usuario.getAvatar() != null ? usuario.getAvatar() : "imagen/default.png");
            
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al insertar usuario: " + e.getMessage(), e);
        }
    }

    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET Email = ?, Password = ?, Nombre = ?, Apellidos = ?, NIF = ?, Telefono = ?, Direccion = ?, CodigoPostal = ?, Localidad = ?, Provincia = ?, Avatar = ? WHERE IdUsuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, usuario.getEmail());
            pst.setString(2, usuario.getPassword());
            pst.setString(3, usuario.getNombre());
            pst.setString(4, usuario.getApellidos());
            pst.setString(5, usuario.getNif());
            pst.setString(6, usuario.getTelefono());
            pst.setString(7, usuario.getDireccion());
            pst.setString(8, usuario.getCodigoPostal());
            pst.setString(9, usuario.getLocalidad());
            pst.setString(10, usuario.getProvincia());
            pst.setString(11, usuario.getAvatar());
            pst.setInt(12, usuario.getIdUsuario());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    public void updatePassword(int idUsuario, String newPassword) {
        String sql = "UPDATE usuarios SET Password = ? WHERE IdUsuario = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, newPassword);
            pst.setInt(2, idUsuario);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar contraseña: " + e.getMessage(), e);
        }
    }

    public void actualizarUltimoAcceso(int idUsuario) {
        String sql = "UPDATE usuarios SET UltimoAcceso = NOW() WHERE IdUsuario = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idUsuario);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
             System.err.println("Error actualizando ultimo acceso: " + e.getMessage());
        }
    }

    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE IdUsuario = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idUsuario);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(crearUsuarioDesdeResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar usuarios: " + e.getMessage(), e);
        }
        return lista;
    }

    public Usuario obtenerPorId(int idUsuario) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE IdUsuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idUsuario);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    usuario = crearUsuarioDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener usuario por ID: " + e.getMessage(), e);
        }
        return usuario;
    }

    public boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE Email = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error verificando email: " + e.getMessage(), e);
        }
        return false;
    }

    private Usuario crearUsuarioDesdeResultSet(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("IdUsuario"),
                rs.getString("Email"),
                rs.getString("Password"),
                rs.getString("Nombre"),
                rs.getString("Apellidos"),
                rs.getString("NIF"),
                rs.getString("Telefono"),
                rs.getString("Direccion"),
                rs.getString("CodigoPostal"),
                rs.getString("Localidad"),
                rs.getString("Provincia"),
                rs.getTimestamp("UltimoAcceso"),
                rs.getString("Avatar"));
    }
}
