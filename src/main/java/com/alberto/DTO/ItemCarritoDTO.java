package com.alberto.DTO;

public class ItemCarritoDTO {
    private int idProducto;
    private String nombre;
    private double precio;
    private int cantidad;
    private String imagen;

    public ItemCarritoDTO() {
    }

    public ItemCarritoDTO(int idProducto, String nombre, double precio, int cantidad, String imagen) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        setImagen(imagen);
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
