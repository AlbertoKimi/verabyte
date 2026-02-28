package com.alberto.Utils;

public class Validaciones {

    // Validar formato de Email
    public static boolean esEmailValido(String email) {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$");
    }

    // Validar seguridad de Contraseña
    public static boolean esPasswordSegura(String password) {
        return password != null && password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }

    // Validar longitud de Teléfono
    public static boolean esTelefonoValido(String telefono) {
        return telefono != null && telefono.matches("^\\d{9}$");
    }

    // Validar longitud de Código Postal
    public static boolean esCPValido(String cp) {
        return cp != null && cp.matches("^\\d{5}$");
    }

    // Validar nombre y apellidos (solo letras y espacios, máx 25 caracteres)
    public static boolean esTextoValido(String texto) {
        return texto != null && texto.length() <= 25 && texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    // Sanitizar texto para evitar desbordamiento en BD
    public static String sanitizarTexto(String texto, int maxLength) {
        if (texto == null) return "";
        if (texto.length() > maxLength) {
            return texto.substring(0, maxLength);
        }
        return texto;
    }
}
