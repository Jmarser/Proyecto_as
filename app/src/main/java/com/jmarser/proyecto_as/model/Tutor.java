package com.jmarser.proyecto_as.model;

import java.util.List;

public class Tutor extends Usuario {

    private String empresa;
    private List<Alumno> alumnosTutor;

    public Tutor() {
        super();
    }

    public Tutor(Long id, String nombre, String primerApellido, String segundoApellido, String email, String empresa, List<Alumno> alumnos) {
        super(id, nombre, primerApellido, segundoApellido, email);
        this.empresa = empresa;
        this.alumnosTutor = alumnos;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public List<Alumno> getAlumnosTutor() {
        return alumnosTutor;
    }

    public void setAlumnosTutor(List<Alumno> alumnosTutor) {
        this.alumnosTutor = alumnosTutor;
    }

    @Override
    public String toString() {
        return super.getNombre() + " " + super.getPrimerApellido() + " " + super.getSegundoApellido();
    }
}
