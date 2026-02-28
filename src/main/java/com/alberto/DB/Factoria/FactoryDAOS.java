package com.alberto.DB.Factoria;

import com.alberto.DB.DAOs.CarritoDAO;
import com.alberto.DB.DAOs.CategoriasDAO;
import com.alberto.DB.DAOs.PedidoDAO;
import com.alberto.DB.DAOs.ProductosDAO;

public abstract class FactoryDAOS {

    public static CarritoDAO crearCarritoDAO(){
        return new CarritoDAO();
    }

    public static CategoriasDAO crearCategoriasDAO(){
        return new CategoriasDAO();
    }

    public static PedidoDAO crearPedidoDAO(){
        return new PedidoDAO();
    }

    public static ProductosDAO crearProductosDAO(){
        return new ProductosDAO();
    }
}

    

