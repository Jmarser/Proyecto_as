package com.jmarser.proyecto_as.model;

import java.util.List;

public class Profesor extends Usuario {

    private List<Alumno> alumnosProf;

    public Profesor() {
        super();
    }

    public Profesor(Long id, String nombre, String primerApellido, String segundoApellido, String email, List<Alumno> alumnos) {
        super(id, nombre, primerApellido, segundoApellido, email);
        this.alumnosProf = alumnos;
    }

    public List<Alumno> getAlumnosProf() {
        return alumnosProf;
    }

    public void setAlumnosProf(List<Alumno> alumnosProf) {
        this.alumnosProf = alumnosProf;
    }

    @Override
    public String toString() {
        return super.getNombre() + " " + super.getPrimerApellido() + " " + super.getSegundoApellido();
    }
}
