package com.example.matuto.misclaves;

public class Cuentas {

    private String nombre;
    private String usuario;
    private String url;
    private String claveC;
    private String leerClave;
    private String Observ;

    public Cuentas(String nombre, String usuario, String url,  String leerClave, String observ) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.url = url;
        this.claveC = claveC;
        this.leerClave = leerClave;
        Observ = observ;
    }

    @Override
    public String toString() {
        return "Cuenta : " +nombre+ "  Web : " +url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getLeerClave() {
        return leerClave;
    }

    public void setLeerClave(String leerClave) {
        this.leerClave = leerClave;
    }

    public String getObserv() {
        return Observ;
    }

    public void setObserv(String observ) {
        Observ = observ;
    }
}
