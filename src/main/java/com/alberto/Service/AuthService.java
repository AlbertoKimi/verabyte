package com.alberto.Service;

import com.alberto.DB.DAOs.UsuariosDAO;
import com.alberto.DTO.UserSessionDTO;
import com.alberto.Model.Usuario;
import com.alberto.Security.PasswordHasherArgon2;

public class AuthService {

    private UsuariosDAO usuariosDAO;

    public AuthService() {
        this.usuariosDAO = new UsuariosDAO();
    }

    public UserSessionDTO login(String email, String password) {
        Usuario user = usuariosDAO.findByEmail(email);

        if (user != null && PasswordHasherArgon2.verifyPassword(user.getPassword(), password.toCharArray())) {
            usuariosDAO.actualizarUltimoAcceso(user.getIdUsuario());
            return new UserSessionDTO((long) user.getIdUsuario(), user.getNombre());
        }
        return null;
    }

    public boolean registrarUsuario(Usuario usuario) {
        if (usuariosDAO.existeEmail(usuario.getEmail())) {
            return false;
        }

        String hashedPassword = PasswordHasherArgon2.hashPassword(usuario.getPassword().toCharArray());
        usuario.setPassword(hashedPassword);

        return usuariosDAO.insertar(usuario);
    }
}
