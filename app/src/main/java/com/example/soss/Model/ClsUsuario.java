package com.example.soss.Model;

public class ClsUsuario {
    public String IdUsuario;
    public String NombreUsuario;
    public String Email;

    public ClsUsuario(String idUsuario, String nombreUsuario, String email) {
        IdUsuario = idUsuario;
        NombreUsuario = nombreUsuario;
        Email = email;
    }

    public String getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
