package com.alberto.Service;

import com.alberto.DB.DAOs.UsuariosDAO;
import com.alberto.DTO.UserSessionDTO;
import com.alberto.Model.Usuario;
import com.alberto.Security.PasswordHasherArgon2;

/**
 * Servicio encargado de gestionar la lógica de autenticación y registro de usuarios.
 * Hace de intermediario entre los controladores y la capa de acceso a datos ({@link UsuariosDAO}).
 */
public class AuthService {

    private UsuariosDAO usuariosDAO;

    /**
     * Inicializa el servicio configurando el acceso a datos.
     */
    public AuthService() {
        this.usuariosDAO = new UsuariosDAO();
    }

    /**
     * Valida las credenciales de un usuario.
     * Si las credenciales son correctas, actualiza su fecha de último acceso en BD.
     *
     * @param email    Correo electrónico del usuario.
     * @param password Contraseña sin encriptar proporcinada por el usuario.
     * @return         Un {@link UserSessionDTO} si el login es exitoso, o {@code null} si falla.
     */
    public UserSessionDTO login(String email, String password) {
        Usuario user = usuariosDAO.findByEmail(email);

        if (user != null && PasswordHasherArgon2.verifyPassword(user.getPassword(), password.toCharArray())) {
            usuariosDAO.actualizarUltimoAcceso(user.getIdUsuario());
            return new UserSessionDTO((long) user.getIdUsuario(), user.getNombre());
        }
        return null;
    }

    /**
     * Procesa el registro de un nuevo usuario encriptando su clave.
     *
     * @param usuario El objeto Usuario a registrar. Debe tener al menos correo y clave en texto plano.
     * @return        {@code true} si se insertó con éxito, {@code false} si ya existía el email o hubo error.
     */
    public boolean registrarUsuario(Usuario usuario) {
        if (usuariosDAO.existeEmail(usuario.getEmail())) {
            return false;
        }

        String hashedPassword = PasswordHasherArgon2.hashPassword(usuario.getPassword().toCharArray());
        usuario.setPassword(hashedPassword);

        return usuariosDAO.insertar(usuario);
    }
}
