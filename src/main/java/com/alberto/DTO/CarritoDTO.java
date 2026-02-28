package com.alberto.DTO;

import java.util.ArrayList;
import java.util.List;

public class CarritoDTO {
    private List<ItemCarritoDTO> items;

    public CarritoDTO() {
        this.items = new ArrayList<>();
    }

    public List<ItemCarritoDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemCarritoDTO> items) {
        this.items = items;
    }

    public double getTotal() {
        double total = 0;
        for (ItemCarritoDTO item : items) {
            total += item.getPrecio() * item.getCantidad();
        }
        return total;
    }

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
