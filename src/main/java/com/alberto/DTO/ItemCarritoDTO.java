package com.alberto.DTO;

/**
 * Objeto de Transferencia de Datos (DTO) que representa un artículo individual 
 * dentro del carrito de compras.
 */
public class ItemCarritoDTO {
    private int idProducto;
    private String nombre;
    private double precio;
    private int cantidad;
    private String imagen;

    /**
     * Constructor por defecto.
     * Crea un ítem de carrito vacío.
     */
    public ItemCarritoDTO() {
    }

    /**
     * Constructor con parámetros.
     * Inicializa un ítem del carrito con todos sus datos.
     *
     * @param idProducto Identificador del producto.
     * @param nombre     Nombre del producto.
     * @param precio     Precio unitario del producto.
     * @param cantidad   Cantidad de unidades seleccionadas.
     * @param imagen     Ruta de la imagen del producto.
     */
    public ItemCarritoDTO(int idProducto, String nombre, double precio, int cantidad, String imagen) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        setImagen(imagen);
    }

    /**
     * Obtiene el identificador del producto.
     * @return El ID del producto.
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador del producto.
     * @param idProducto El nuevo ID del producto.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene el nombre del producto.
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre El nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio unitario del producto.
     * @return El precio unitario.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio unitario del producto.
     * @param precio El nuevo precio unitario.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la cantidad seleccionada de este producto.
     * @return La cantidad.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad seleccionada para este producto.
     * @param cantidad La nueva cantidad.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene la ruta o URL de la imagen del producto.
     * @return La ruta de la imagen.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen del producto aplicando normalización.
     * Si la imagen es nula o vacía, asigna un placeholder. Si no
     * especifica extensión, le agrega '.jpg'. Además se asegura de
     * que la ruta comience con 'Imagenes/productos/'.
     *
     * @param imagen La URL o nombre de la imagen.
     */
    public void setImagen(String imagen) {
        if (imagen == null || imagen.trim().isEmpty()) {
            this.imagen = "Imagenes/placeholder.png";
        } else {
            String img = imagen.trim();
            if (!img.endsWith(".jpg") && !img.endsWith(".png") && !img.endsWith(".jpeg") && !img.endsWith(".gif")) {
                img += ".jpg";
            }
            if (!img.startsWith("Imagenes/productos/")) {
                img = "Imagenes/productos/" + img;
            }
            this.imagen = img;
        }
    }
}
