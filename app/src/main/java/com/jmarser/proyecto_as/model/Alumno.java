package com.jmarser.proyecto_as.model;


import java.util.List;

public class Alumno extends Usuario {

    private String ciclo;
    private Profesor profesor;
    private Tutor tutor;
    private List<Ficha> fichas;

    public Alumno() {
        super();
    }

    public Alumno(Long id, String nombre, String primerApellido, String segundoApellido, String email, String ciclo, Profesor profesor, Tutor tutor, List<Ficha> listadoFichas) {
        super(id, nombre, primerApellido, segundoApellido, email);
        this.ciclo = ciclo;
        this.profesor = profesor;
        this.tutor = tutor;
        this.fichas = listadoFichas;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public List<Ficha> getFichas() {
        return fichas;
    }

    public void setFichas(List<Ficha> fichas) {
        this.fichas = fichas;
    }

    @Override
    public String toString() {
        return super.getNombre() + " " + super.getPrimerApellido() + " " + super.getSegundoApellido();
    }
}
