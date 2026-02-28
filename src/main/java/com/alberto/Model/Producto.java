package com.alberto.Model;

/**
 * Representa un producto en el catálogo de la tienda.
 * Contiene información detallada sobre el producto, como su nombre, descripción, precio y marca.
 */
public class Producto {

    private int idProducto;
    private String idCategoria;
    private String nombre;
    private String descripcion;
    private double precio;
    private String marca;
    private String imagen;


    /**
     * Constructor por defecto.
     * Crea una instancia de Producto vacía.
     */
    public Producto() {
    }


    /**
     * Constructor con parámetros.
     * Crea una instancia de Producto inicializando todos sus atributos.
     *
     * @param idProducto  Identificador único del producto.
     * @param idCategoria Identificador de la categoría a la que pertenece el producto.
     * @param nombre      Nombre del producto.
     * @param descripcion Descripción detallada del producto.
     * @param precio      Precio del producto.
     * @param marca       Marca o fabricante del producto.
     * @param imagen      Ruta o URL de la imagen del producto.
     */
    public Producto(int idProducto, String idCategoria, String nombre, String descripcion, double precio, String marca, String imagen) {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.marca = marca;
        this.imagen = imagen;
    }


    /**
     * Obtiene el identificador del producto.
     * @return El ID del producto.
     */
    public int getIdProducto() {
        return this.idProducto;
    }

    /**
     * Establece el identificador del producto.
     * @param idProducto El nuevo ID del producto.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene el identificador de la categoría del producto.
     * @return El ID de la categoría.
     */
    public String getIdCategoria() {
        return this.idCategoria;
    }

    /**
     * Establece el identificador de la categoría del producto.
     * @param idCategoria El nuevo ID de la categoría.
     */
    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Obtiene el nombre del producto.
     * @return El nombre del producto.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre El nuevo nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del producto.
     * @return La descripción del producto.
     */
    public String getDescripcion() {
        return this.descripcion;
    }

    /**
     * Establece la descripción del producto.
     * @param descripcion La nueva descripción del producto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el precio del producto.
     * @return El precio del producto.
     */
    public double getPrecio() {
        return this.precio;
    }

    /**
     * Establece el precio del producto.
     * @param precio El nuevo precio del producto.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la marca fabricadora del producto.
     * @return La marca del producto.
     */
    public String getMarca() {
        return this.marca;
    }

    /**
     * Establece la marca fabricadora del producto.
     * @param marca La nueva marca del producto.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene la ruta o URL de la imagen del producto.
     * @return La imagen del producto.
     */
    public String getImagen() {
        return this.imagen;
    }

    /**
     * Establece la ruta o URL de la imagen del producto.
     * @param imagen La nueva imagen del producto.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }





    
}
