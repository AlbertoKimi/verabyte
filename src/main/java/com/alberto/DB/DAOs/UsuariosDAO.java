package com.alberto.DB.DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.alberto.DB.Conexion;
import com.alberto.Model.Usuario;

/**
 * Clase de Acceso a Datos (DAO) encargada de gestionar los {@link Usuario}s en la base de datos.
 * Maneja el registro, edición, eliminación, inicio de sesión y validación de credenciales.
 */
public class UsuariosDAO {

    /**
     * Busca un usuario en la base de datos utilizando su dirección de correo electrónico.
     * Útil para los procesos de inicio de sesión y para comprobar duplicados.
     *
     * @param email El correo electrónico del usuario a buscar.
     * @return El objeto {@link Usuario} si se encuentra, o nulo si no existe.
     * @throws RuntimeException si hay un fallo en la conexión o consulta SQL.
     */
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

    /**
     * Inserta un nuevo usuario en la base de datos (registro).
     *
     * @param usuario El objeto {@link Usuario} con todos los datos a guardar.
     * @return true si el usuario se registró correctamente (filas afectadas > 0), false en caso contrario.
     * @throws RuntimeException En caso de problema al ejecutar el INSERT.
     */
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

    /**
     * Actualiza la información de un usuario existente en la base de datos.
     *
     * @param usuario El objeto {@link Usuario} con los datos actualizados, incluyendo su ID obligatorio.
     * @return true si la actualización fue exitosa, false de lo contrario.
     * @throws RuntimeException En caso de fallo de SQL al hacer el UPDATE.
     */
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

    /**
     * Actualiza únicamente la contraseña de un usuario encriptada.
     *
     * @param idUsuario   El ID del usuario destinatario del cambio.
     * @param newPassword La nueva contraseña ya hasheada.
     * @throws RuntimeException Si hay un error SQL.
     */
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

    /**
     * Actualiza el campo 'UltimoAcceso' del usuario a la fecha y hora actuales.
     *
     * @param idUsuario El ID del usuario que acaba de iniciar sesión.
     */
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

    /**
     * Elimina un usuario de la base de datos.
     * ¡Atención! Puede fallar por restricciones de claves foráneas si el usuario tiene pedidos.
     *
     * @param idUsuario El ID del usuario a eliminar.
     * @return true si se eliminó correctamente.
     * @throws RuntimeException Si hay un error SQL, como violación de Foreign Key.
     */
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

    /**
     * Recupera todos los usuarios registrados en el sistema.
     * Utilizado generalmente en paneles de administración.
     *
     * @return Una lista de objetos {@link Usuario}.
     * @throws RuntimeException En caso de error SQL.
     */
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

    /**
     * Busca y devuelve un usuario determinado por su ID.
     *
     * @param idUsuario El identificador único del usuario.
     * @return El objeto {@link Usuario}, o nulo si no se encuentra.
     * @throws RuntimeException Si falla la base de datos.
     */
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

    /**
     * Verifica si un correo electrónico ya está registrado en la base de datos de usuarios.
     *
     * @param email El correo electrónico a comprobar.
     * @return true si el email ya existe, false si está disponible.
     * @throws RuntimeException por errores SQL.
     */
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

    /**
     * Método auxiliar privado para convertir la fila actual de un {@link ResultSet}
     * en un objeto estructurado {@link Usuario}.
     *
     * @param rs El ResultSet posicionado en la fila del usuario.
     * @return El objeto de dominio Usuario relleno.
     * @throws SQLException Si un campo no existe o hay problemas de acceso a datos.
     */
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
