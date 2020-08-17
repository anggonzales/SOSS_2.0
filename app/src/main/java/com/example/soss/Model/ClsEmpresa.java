package com.example.soss.Model;

public class ClsEmpresa {
    private String IdEmpresa;
    private String id;
    private String Horario;
    private String Correo;
    private String Nombre;
    private String Latitud;
    private String Longitud;
    private Double RatingTotal;
    private Long RatingConteo;

    public Double getRatingTotal() {
        return RatingTotal;
    }

    public void setRatingTotal(Double ratingTotal) {
        RatingTotal = ratingTotal;
    }

    public Long getRatingConteo() {
        return RatingConteo;
    }

    public void setRatingConteo(Long ratingConteo) {
        RatingConteo = ratingConteo;
    }

    public ClsEmpresa() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEmpresa() {
        return IdEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        IdEmpresa = idEmpresa;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        Horario = horario;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }
}
