package com.alberto.Utils;

/**
 * Clase utilitaria que provee métodos estáticos para validar el formato de varios
 * tipos de información de usuario (email, contraseñas, teléfonos, textos planos).
 */
public class Validaciones {

    /**
     * Comprueba mediante Expresión Regular si un email luce correcto estructuralmente.
     * @param email Cadena a validar.
     * @return true si es válido, false en caso contrario.
     */
    public static boolean esEmailValido(String email) {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$");
    }

    /**
     * Comprueba mediante Expresión Regular si una contraseña cumple con reglas de complejidad.
     * Mínimo 8 caracteres, al menos una mayúscula, una minúscula y un número.
     * @param password Cadena a validar.
     * @return true si es segura, false en caso contrario.
     */
    public static boolean esPasswordSegura(String password) {
        return password != null && password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }

    /**
     * Comprueba que un teléfono tenga exactamente 9 dígitos.
     * @param telefono Cadena numérica.
     * @return true si es válido, false en caso contrario.
     */
    public static boolean esTelefonoValido(String telefono) {
        return telefono != null && telefono.matches("^\\d{9}$");
    }

    /**
     * Comprueba que un código postal tenga exactamente 5 dígitos.
     * @param cp Cadena numérica.
     * @return true si es válido, false en caso contrario.
     */
    public static boolean esCPValido(String cp) {
        return cp != null && cp.matches("^\\d{5}$");
    }

    /**
     * Comprueba que un texto (como un nombre o apellido) tenga solo letras y espacios,
     * con una longitud no superior a 25 caracteres.
     * @param texto Cadena de texto a analizar.
     * @return true si pasa la validación, false en caso contrario.
     */
    public static boolean esTextoValido(String texto) {
        return texto != null && texto.length() <= 25 && texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    /**
     * Permite acortar un texto (String truncation) para prevenir desbordamientos en columnas SQL.
     * @param texto      Texto original completo.
     * @param maxLength  Cota superior de longitud.
     * @return           Texto truncado a la longitud máxima especificada.
     */
    public static String sanitizarTexto(String texto, int maxLength) {
        if (texto == null) return "";
        if (texto.length() > maxLength) {
            return texto.substring(0, maxLength);
        }
        return texto;
    }
}
