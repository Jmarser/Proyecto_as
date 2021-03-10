package com.jmarser.proyecto_as.model;

import android.os.Parcel;

import java.util.List;

public class Tutor extends Usuario {

    private String empresa;
    private List<Alumno> alumnos;

    public Tutor() {
        super();
    }

    public Tutor(Long id, String nombre, String primerApellido, String segundoApellido, String email, String empresa, List<Alumno> alumnos) {
        super(id, nombre, primerApellido, segundoApellido, email);
        this.empresa = empresa;
        this.alumnos = alumnos;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    @Override
    public String toString() {
        return super.getNombre() + " " + super.getPrimerApellido() + " " + super.getSegundoApellido();
    }
}
