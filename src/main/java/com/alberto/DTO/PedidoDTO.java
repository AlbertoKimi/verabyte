package com.alberto.DTO;

import java.sql.Date;

/**
 * Objeto de Transferencia de Datos (DTO) para llevar la información 
 * de un Pedido entre capas (UI y lógica de negocio).
 */
public class PedidoDTO {
    private int idPedido;
    private Date fecha;
    private String estado;
    private int idUsuario;
    private double importe;
    private double iva;

    /**
     * Constructor por defecto.
     * Crea un DTO de pedido vacío.
     */
    public PedidoDTO() {}

    /**
     * Constructor con parámetros.
     * Inicializa el DTO con toda la información del pedido.
     *
     * @param idPedido  El ID único del pedido.
     * @param fecha     La fecha del pedido.
     * @param estado    El estado del pedido.
     * @param idUsuario El ID del usuario asociado.
     * @param importe   El coste total.
     * @param iva       Los impuestos aplicados.
     */
    public PedidoDTO(int idPedido, Date fecha, String estado, int idUsuario, double importe, double iva) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.importe = importe;
        this.iva = iva;
    }

    /**
     * Obtiene el identificador del pedido.
     * @return El ID del pedido.
     */
    public int getIdPedido() { return idPedido; }
    /**
     * Establece el identificador del pedido.
     * @param idPedido El nuevo ID.
     */
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    /**
     * Obtiene la fecha del pedido.
     * @return La fecha en formato SQL.
     */
    public Date getFecha() { return fecha; }
    /**
     * Establece la fecha del pedido.
     * @param fecha La nueva fecha.
     */
    public void setFecha(Date fecha) { this.fecha = fecha; }

    /**
     * Obtiene el estado del pedido.
     * @return El estado.
     */
    public String getEstado() { return estado; }
    /**
     * Establece el estado del pedido.
     * @param estado El nuevo estado.
     */
    public void setEstado(String estado) { this.estado = estado; }

    /**
     * Obtiene el ID del usuario.
     * @return El ID del usuario.
     */
    public int getIdUsuario() { return idUsuario; }
    /**
     * Establece el ID del usuario del pedido.
     * @param idUsuario El nuevo ID de usuario.
     */
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    /**
     * Obtiene el importe total del pedido sin impuestos.
     * @return El importe total.
     */
    public double getImporte() { return importe; }
    /**
     * Establece el importe total.
     * @param importe El nuevo importe.
     */
    public void setImporte(double importe) { this.importe = importe; }

    /**
     * Obtiene los impuestos aplicados.
     * @return Los impuestos aplicados (ej. IVA).
     */
    public double getIva() { return iva; }
    /**
     * Establece los impuestos.
     * @param iva El nuevo valor de impuestos.
     */
    public void setIva(double iva) { this.iva = iva; }
}
