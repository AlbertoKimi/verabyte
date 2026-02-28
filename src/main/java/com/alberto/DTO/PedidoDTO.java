package com.alberto.DTO;

import java.sql.Date;

public class PedidoDTO {
    private int idPedido;
    private Date fecha;
    private String estado;
    private int idUsuario;
    private double importe;
    private double iva;

    public PedidoDTO() {}

    public PedidoDTO(int idPedido, Date fecha, String estado, int idUsuario, double importe, double iva) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.importe = importe;
        this.iva = iva;
    }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public double getImporte() { return importe; }
    public void setImporte(double importe) { this.importe = importe; }

    public double getIva() { return iva; }
    public void setIva(double iva) { this.iva = iva; }
}
