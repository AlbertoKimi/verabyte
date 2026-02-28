package com.alberto.Model;

public class Producto {

    private int idProducto;
    private String idCategoria;
    private String nombre;
    private String descripcion;
    private double precio;
    private String marca;
    private String imagen;


    public Producto() {
    }


    public Producto(int idProducto, String idCategoria, String nombre, String descripcion, double precio, String marca, String imagen) {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.marca = marca;
        this.imagen = imagen;
    }


    public int getIdProducto() {
        return this.idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdCategoria() {
        return this.idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }





    
}
