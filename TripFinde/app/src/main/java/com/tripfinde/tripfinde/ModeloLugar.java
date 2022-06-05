package com.tripfinde.tripfinde;

public class ModeloLugar {
    private String idLugar;
    private String departamento;
    private String nombre;
    private String descripcion;
    private String portada;
    private String tipoLugar;

    public ModeloLugar(String idLugar, String departamento, String nombre, String descripcion, String portada, String tipoLugar) {
        this.idLugar = idLugar;
        this.departamento = departamento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.portada = portada;
        this.tipoLugar = tipoLugar;
    }

    public ModeloLugar() {
    }

    public String getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(String idLugar) {
        this.idLugar = idLugar;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getTipoLugar() {
        return tipoLugar;
    }

    public void setTipoLugar(String tipoLugar) {
        this.tipoLugar = tipoLugar;
    }
}
