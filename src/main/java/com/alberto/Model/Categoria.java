package com.alberto.Model;

/**
 * Representa una categoría de productos en el sistema.
 * Contiene información básica como el identificador, nombre y la URL de su imagen.
 */
public class Categoria {
    
    private int idCategoria; 
    private String nombre;
    private String imagen;


    /**
     * Constructor por defecto.
     * Crea una instancia de Categoria sin inicializar sus atributos.
     */
    public Categoria() {
    }


    /**
     * Constructor con parámetros.
     * Crea una instancia de Categoria inicializando todos sus atributos.
     *
     * @param idCategoria Identificador único de la categoría.
     * @param nombre      Nombre descriptivo de la categoría.
     * @param imagen      Ruta o URL de la imagen representativa de la categoría.
     */
    public Categoria(int idCategoria, String nombre, String imagen) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.imagen = imagen;
    }


    /**
     * Obtiene el identificador de la categoría.
     *
     * @return El identificador de la categoría.
     */
    public int getIdCategoria() {
        return this.idCategoria;
    }

    /**
     * Establece el identificador de la categoría.
     *
     * @param idCategoria El nuevo identificador para la categoría.
     */
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Obtiene el nombre de la categoría.
     *
     * @return El nombre de la categoría.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param nombre El nuevo nombre para la categoría.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la ruta de la imagen de la categoría.
     *
     * @return La ruta o URL de la imagen.
     */
    public String getImagen() {
        return this.imagen;
    }

    /**
     * Establece la ruta de la imagen de la categoría.
     *
     * @param imagen La nueva ruta o URL de la imagen.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }



}
