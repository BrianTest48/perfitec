package com.brianc.myapplication.models;

public class Maquina {
    String id, descripcion, anio;

    public Maquina() {
    }

    public Maquina(String id, String descripcion, String anio) {
        this.id = id;
        this.descripcion = descripcion;
        this.anio = anio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }
}
