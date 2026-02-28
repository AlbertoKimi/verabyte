package com.alberto.DTO;

import com.alberto.Model.Producto;

/**
 * Objeto de Transferencia de Datos (DTO) para llevar la información 
 * detallada de un Producto.
 */
public class ProductoDTO {

    private int idProducto;
    private String idCategoria;
    private String nombre;
    private String descripcion;
    private double precio;
    private String marca;
    private String imagen;

    /**
     * Constructor por defecto.
     * Crea un DTO de producto vacío.
     */
    public ProductoDTO() {
    }

    /**
     * Constructor que mapea desde una entidad {@link Producto}.
     * Copia todos los atributos del modelo al DTO.
     *
     * @param producto La entidad Producto de origen.
     */
    public ProductoDTO(Producto producto) {
        this.idProducto = producto.getIdProducto();
        this.idCategoria = producto.getIdCategoria();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.precio = producto.getPrecio();
        this.marca = producto.getMarca();
        setImagen(producto.getImagen());
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
     * @param idProducto El nuevo ID.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene el ID de la categoría del producto.
     * @return El ID de la categoría.
     */
    public String getIdCategoria() {
        return idCategoria;
    }

    /**
     * Establece el ID de la categoría del producto.
     * @param idCategoria El nuevo ID de categoría.
     */
    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Obtiene el nombre del producto.
     * @return El nombre.
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
     * Obtiene la descripción detallada del producto.
     * @return La descripción.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     * @param descripcion La nueva descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el precio del producto.
     * @return El precio.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     * @param precio El nuevo precio.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la marca fabricadora del producto.
     * @return La marca.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Establece la marca del producto.
     * @param marca La nueva marca.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Obtiene la ruta o URL de la imagen del producto.
     * @return La imagen.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen del producto con normalización.
     * Aplica la misma lógica que el {@link ItemCarritoDTO} para
     * agregar un placeholder o normalizar la ruta de la imagen.
     *
     * @param imagen La ruta o nombre descriptivo de la imagen.
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
