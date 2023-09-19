package com.brianc.myapplication.models;

public class Cliente {

    String id, descripcion, ruc, direccion, celular;

    public Cliente() {
    }

    public Cliente(String id, String descripcion, String ruc, String direccion, String celular) {
        this.id = id;
        this.descripcion = descripcion;
        this.ruc = ruc;
        this.direccion = direccion;
        this.celular = celular;
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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
