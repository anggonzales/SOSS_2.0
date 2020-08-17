package com.example.soss.Model;

public class ClsPagoServicio {
    private String id;
    private String IdPago;
    private String IdUsuario;
    private Double Precio;
    private String Fecha;
    private String Estado;

    public ClsPagoServicio() {
    }

    public ClsPagoServicio(String idUsuario, Double precio, String fecha, String estado) {
        IdUsuario = idUsuario;
        Precio = precio;
        Fecha = fecha;
        Estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPago() {
        return IdPago;
    }

    public void setIdPago(String idPago) {
        IdPago = idPago;
    }

    public String getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        IdUsuario = idUsuario;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
