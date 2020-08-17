package com.example.soss.Model;

import java.util.Map;

public class ClsRating {
    private float ValorRating;
    private String Comentario,Nombre,Uid;
    private Map<String,Object> ComanetarioTimeStamp;

    public ClsRating() {
    }

    public float getValorRating() {
        return ValorRating;
    }

    public void setValorRating(float valorRating) {
        ValorRating = valorRating;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public Map<String, Object> getComanetarioTimeStamp() {
        return ComanetarioTimeStamp;
    }

    public void setComanetarioTimeStamp(Map<String, Object> comanetarioTimeStamp) {
        ComanetarioTimeStamp = comanetarioTimeStamp;
    }
}
