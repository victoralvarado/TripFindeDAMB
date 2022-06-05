package com.tripfinde.tripfinde;

public class ModeloDepartamento {
    private String idDepartamento;
    private String nombre;
    private String portada;

    public ModeloDepartamento(String idDepartamento, String nombre, String portada) {
        this.idDepartamento = idDepartamento;
        this.nombre = nombre;
        this.portada = portada;
    }

    public ModeloDepartamento() {
    }

    public String getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }
}
