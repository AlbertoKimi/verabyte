package com.alberto.Model;

import java.sql.Timestamp;

/**
 * Representa un usuario del sistema (cliente o administrador).
 * Contiene toda la información personal, de contacto y credenciales del usuario.
 */
public class Usuario {
    private int idUsuario;
    private String email;
    private String password;
    private String nombre;
    private String apellidos;
    private String nif;
    private String telefono;
    private String direccion;
    private String codigoPostal;
    private String localidad;
    private String provincia;
    private Timestamp ultimoAcceso;
    private String avatar;

    /**
     * Constructor por defecto.
     * Crea una instancia de Usuario vacía.
     */
    public Usuario() {
    }

    /**
     * Constructor con parámetros.
     * Crea una instancia de Usuario inicializando todos sus campos.
     *
     * @param idUsuario    Identificador único del usuario.
     * @param email        Correo electrónico (usado normalmente para login).
     * @param password     Contraseña en formato encriptado.
     * @param nombre       Nombre del usuario.
     * @param apellidos    Apellidos del usuario.
     * @param nif          Número de Identificación Fiscal.
     * @param telefono     Número de teléfono de contacto.
     * @param direccion    Dirección física.
     * @param codigoPostal Código postal de la dirección.
     * @param localidad    Localidad o ciudad.
     * @param provincia    Provincia o región.
     * @param ultimoAcceso Marca de tiempo del último inicio de sesión.
     * @param avatar       Ruta o URL de la imagen de perfil.
     */
    public Usuario(int idUsuario, String email, String password, String nombre, String apellidos, String nif,
            String telefono, String direccion, String codigoPostal, String localidad, String provincia,
            Timestamp ultimoAcceso, String avatar) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nif = nif;
        this.telefono = telefono;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.ultimoAcceso = ultimoAcceso;
        this.avatar = avatar;
    }

    /**
     * Obtiene el identificador del usuario.
     * @return El ID del usuario.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el identificador del usuario.
     * @param idUsuario El nuevo ID del usuario.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return El email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     * @param email El nuevo email del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario (típicamente encriptada).
     * @return La contraseña.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     * @param password La nueva contraseña.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre El nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del usuario.
     * @return Los apellidos.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del usuario.
     * @param apellidos Los nuevos apellidos.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el Número de Identificación Fiscal del usuario.
     * @return El NIF.
     */
    public String getNif() {
        return nif;
    }

    /**
     * Establece el NIF del usuario.
     * @param nif El nuevo NIF.
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     * Obtiene el teléfono del usuario.
     * @return El número de teléfono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del usuario.
     * @param telefono El nuevo teléfono.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la dirección del usuario.
     * @return La dirección.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del usuario.
     * @param direccion La nueva dirección.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el código postal.
     * @return El código postal.
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Establece el código postal.
     * @param codigoPostal El nuevo código postal.
     */
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * Obtiene la localidad.
     * @return La localidad.
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Establece la localidad.
     * @param localidad La nueva localidad.
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     * Obtiene la provincia.
     * @return La provincia.
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Establece la provincia.
     * @param provincia La nueva provincia.
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * Obtiene la fecha y hora del último acceso exitoso del usuario.
     * @return El Timestamp del último acceso.
     */
    public Timestamp getUltimoAcceso() {
        return ultimoAcceso;
    }

    /**
     * Establece la fecha y hora del último acceso.
     * @param ultimoAcceso El nuevo tiempo de acceso.
     */
    public void setUltimoAcceso(Timestamp ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    /**
     * Obtiene la ruta de la imagen del avatar del usuario.
     * @return La ruta del avatar.
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Establece la ruta del avatar del usuario.
     * @param avatar La nueva ruta de la imagen.
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * Representación en forma de cadena de texto de los datos del usuario.
     * @return Una cadena con los valores de los atributos del usuario.
     */
    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", email=" + email + ", password=" + password + ", nombre=" + nombre
                + ", apellidos=" + apellidos + ", nif=" + nif + ", telefono=" + telefono + ", direccion=" + direccion
                + ", codigoPostal=" + codigoPostal + ", localidad=" + localidad + ", provincia=" + provincia
                + ", ultimoAcceso=" + ultimoAcceso + ", avatar=" + avatar + "]";
    }
}
