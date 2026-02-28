package com.alberto.DTO;

import com.alberto.Model.Producto;

public class ProductoDTO {

    private int idProducto;
    private String idCategoria;
    private String nombre;
    private String descripcion;
    private double precio;
    private String marca;
    private String imagen;

    public ProductoDTO() {
    }

    public ProductoDTO(Producto producto) {
        this.idProducto = producto.getIdProducto();
        this.idCategoria = producto.getIdCategoria();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.precio = producto.getPrecio();
        this.marca = producto.getMarca();
        setImagen(producto.getImagen());
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getImagen() {
        return imagen;
    }

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
