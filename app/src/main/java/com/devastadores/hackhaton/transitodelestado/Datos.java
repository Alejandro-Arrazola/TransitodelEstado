package com.devastadores.hackhaton.transitodelestado;

/**
 * Created by Alejandro on 28/10/2015.
 */
public class Datos {
    private String suceso;
    private String latitud;
    private String longitud;
    private String ubicacion;
    private String estado;
    private String altura="16z";

    public Datos(String suceso, String latitud, String longitud, String estado, String ubicacion) {
        this.suceso = suceso;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
        this.ubicacion = ubicacion;
    }

    public String getSuceso() {
        return suceso;
    }

    public void setSuceso(String suceso) {
        this.suceso = suceso;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    @Override
    public String toString() {
        return  "Se Reporta  " + suceso + '\'' +
                "En : "+ ubicacion+"  Actualmente " + estado ;
    }
}
