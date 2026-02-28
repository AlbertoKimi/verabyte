package com.alberto.Model;

import java.sql.Date;

/**
 * Representa un pedido realizado por un usuario en el sistema.
 * Contiene información sobre la fecha, estado, importe e impuestos aplicados.
 */
public class Pedido {
    private int idPedido;
    private Date fecha;
    private String estado;
    private int idUsuario;
    private double importe;
    private double iva;

    /**
     * Constructor por defecto.
     * Crea una instancia de Pedido vacía.
     */
    public Pedido() {}

    /**
     * Constructor con parámetros.
     * Crea una instancia de Pedido inicializando todos sus campos.
     *
     * @param idPedido  Identificador único del pedido.
     * @param fecha     Fecha en la que se realizó el pedido.
     * @param estado    Estado actual del pedido (ej. "Pendiente", "Enviado", "Entregado").
     * @param idUsuario Identificador del usuario que realizó el pedido.
     * @param importe   Importe total del pedido.
     * @param iva       Importe correspondiente al IVA u otros impuestos.
     */
    public Pedido(int idPedido, Date fecha, String estado, int idUsuario, double importe, double iva) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.importe = importe;
        this.iva = iva;
    }

    /**
     * Obtiene el identificador del pedido.
     * @return El identificador del pedido.
     */
    public int getIdPedido() { return idPedido; }
    /**
     * Establece el identificador del pedido.
     * @param idPedido El nuevo identificador del pedido.
     */
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    /**
     * Obtiene la fecha del pedido.
     * @return La fecha en que se realizó el pedido.
     */
    public Date getFecha() { return fecha; }
    /**
     * Establece la fecha del pedido.
     * @param fecha La nueva fecha para el pedido.
     */
    public void setFecha(Date fecha) { this.fecha = fecha; }

    /**
     * Obtiene el estado del pedido.
     * @return El estado del pedido.
     */
    public String getEstado() { return estado; }
    /**
     * Establece el estado del pedido.
     * @param estado El nuevo estado para el pedido.
     */
    public void setEstado(String estado) { this.estado = estado; }

    /**
     * Obtiene el identificador del usuario que realizó el pedido.
     * @return El ID del usuario.
     */
    public int getIdUsuario() { return idUsuario; }
    /**
     * Establece el identificador del usuario asociado al pedido.
     * @param idUsuario El ID del usuario.
     */
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    /**
     * Obtiene el importe total del pedido.
     * @return El importe total.
     */
    public double getImporte() { return importe; }
    /**
     * Establece el importe total del pedido.
     * @param importe El nuevo importe total.
     */
    public void setImporte(double importe) { this.importe = importe; }

    /**
     * Obtiene el importe de los impuestos (IVA) aplicados al pedido.
     * @return El importe de los impuestos.
     */
    public double getIva() { return iva; }
    /**
     * Establece el importe de los impuestos (IVA).
     * @param iva El nuevo importe de impuestos.
     */
    public void setIva(double iva) { this.iva = iva; }
}
