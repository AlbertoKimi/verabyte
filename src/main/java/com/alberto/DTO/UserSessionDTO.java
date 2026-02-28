package com.alberto.DTO;

import java.io.Serializable;

/**
 * Objeto de Transferencia de Datos (DTO) diseñado para almacenar 
 * un subconjunto ligero y seguro de la información del usuario en la sesión HTTP.
 */
public class UserSessionDTO implements Serializable {

    private Long userId;
    private String username;

    /**
     * Constructor con parámetros.
     * Inicializa el DTO con el ID del usuario y su nombre de usuario.
     *
     * @param userId   Identificador del usuario.
     * @param username Nombre del usuario.
     */
    public UserSessionDTO(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    /**
     * Obtiene el identificador del usuario.
     * @return El ID del usuario.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return El nombre o alias.
     */
    public String getUsername() {
        return username;
    }
}