package com.alberto.Model;

public class Categoria {
    
    private int idCategoria; 
    private String nombre;
    private String imagen;


    public Categoria() {
    }


    public Categoria(int idCategoria, String nombre, String imagen) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.imagen = imagen;
    }


    public int getIdCategoria() {
        return this.idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }



}
