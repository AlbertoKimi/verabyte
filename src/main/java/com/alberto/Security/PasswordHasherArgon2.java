package com.alberto.Security;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 * Clase utilitaria para encriptar y verificar contraseñas utilizando el algoritmo Argon2.
 * Implementa las mejores prácticas borrando los arrays de memoria tras su uso.
 */
public class PasswordHasherArgon2 {
    private static final int ITERATIONS = 10;
    private static final int MEMORY = 65536;
    private static final int PARALLELISM = 1;

    private static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    /**
     * Hashea o encripta una cadena de caracteres usando configuración Argon2.
     *
     * @param password Contraseña original introducida por el usuario como array de caracteres.
     * @return         Cadena segura codificada y encriptada, lista para almacenar.
     */
    public static String hashPassword(char[] password) {
        try {
            return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password);
        } finally {
            argon2.wipeArray(password);
        }
    }

    /**
     * Verifica si una contraseña proporcionada en texto concuerda con un hash almacenado.
     *
     * @param hash     El hash criptográfico recuperado de la BD.
     * @param password La contraseña ingresada por el usuario al hacer login (array de caracteres).
     * @return         {@code true} si coinciden, {@code false} en caso contrario.
     */
    public static boolean verifyPassword(String hash, char[] password) {
        try {
            return argon2.verify(hash, password);
        } finally {
            argon2.wipeArray(password);
        }
    }
}
