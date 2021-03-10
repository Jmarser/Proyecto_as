package com.jmarser.proyecto_as.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Profesor extends Usuario {

    private List<Alumno> alumnos;

    public Profesor() {
        super();
    }

    public Profesor(Long id, String nombre, String primerApellido, String segundoApellido, String email, List<Alumno> alumnos) {
        super(id, nombre, primerApellido, segundoApellido, email);
        this.alumnos = alumnos;
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
