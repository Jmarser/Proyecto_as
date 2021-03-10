package com.jmarser.proyecto_as.model;

import java.io.Serializable;
import java.util.Date;

public class Ficha implements Serializable {

    private Long id;
    private String descripcion;
    private String observaciones;
    private boolean firmaAlumno;
    private boolean firmaProf;
    private boolean firmaTutor;
    private int horas;
    private Date fecha;

    public Ficha() {
    }

    public Ficha(Long id, String descripcion, String observaciones, boolean firma_alumno, boolean firma_profesor, boolean firma_tutor, int horas, Date fecha) {
        this.id = id;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
        this.firmaAlumno = firma_alumno;
        this.firmaProf = firma_profesor;
        this.firmaTutor = firma_tutor;
        this.horas = horas;
        this.fecha = fecha;
    }

    public boolean isFirmaAlumno() {
        return firmaAlumno;
    }

    public void setFirmaAlumno(boolean firmaAlumno) {
        this.firmaAlumno = firmaAlumno;
    }

    public boolean isFirmaProf() {
        return firmaProf;
    }

    public void setFirmaProf(boolean firmaProf) {
        this.firmaProf = firmaProf;
    }

    public boolean isFirmaTutor() {
        return firmaTutor;
    }

    public void setFirmaTutor(boolean firmaTutor) {
        this.firmaTutor = firmaTutor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
