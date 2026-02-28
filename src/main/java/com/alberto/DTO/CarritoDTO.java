package com.alberto.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Transferencia de Datos (DTO) que representa el carrito de compras.
 * Contiene una lista de los artículos agregados y métodos de conveniencia para 
 * calcular el total.
 */
public class CarritoDTO {
    private List<ItemCarritoDTO> items;

    /**
     * Constructor por defecto.
     * Inicializa la lista de artículos vacía.
     */
    public CarritoDTO() {
        this.items = new ArrayList<>();
    }

    /**
     * Obtiene la lista de artículos en el carrito.
     * @return La lista de {@link ItemCarritoDTO}.
     */
    public List<ItemCarritoDTO> getItems() {
        return items;
    }

    /**
     * Establece la lista de artículos del carrito.
     * @param items La nueva lista de artículos.
     */
    public void setItems(List<ItemCarritoDTO> items) {
        this.items = items;
    }

    /**
     * Calcula el importe total del carrito sumando el precio * cantidad
     * de cada uno de sus artículos.
     * 
     * @return El importe total.
     */
    public double getTotal() {
        double total = 0;
        for (ItemCarritoDTO item : items) {
            total += item.getPrecio() * item.getCantidad();
        }
        return total;
    }

    /**
     * Añade un nuevo artículo al carrito. 
     * Si el producto ya existe en el carrito, se suma la cantidad en lugar de
     * añadir un nuevo elemento a la lista.
     * 
     * @param newItem El artículo a añadir.
     */
    public void addItem(ItemCarritoDTO newItem) {
        for (ItemCarritoDTO item : items) {
            if (item.getIdProducto() == newItem.getIdProducto()) {
                item.setCantidad(item.getCantidad() + newItem.getCantidad());
                return;
            }
        }
        items.add(newItem);
    }
}
