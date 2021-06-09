package com.jmarser.proyecto_as.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Ficha implements Parcelable {

    private Long id;
    private String descripcion;
    private String observaciones;
    private Long alumnoId;
    private boolean firmaAlumno;
    private boolean firmaProf;
    private boolean firmaTutor;
    private int horas;
    private String fecha;
    private String ciclo;

    public Ficha() {
    }

    public Ficha(Long id, String descripcion, String observaciones, Long alumnoId, boolean firmaAlumno, boolean firmaProf, boolean firmaTutor, int horas, String fecha, String ciclo) {
        this.id = id;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
        this.alumnoId = alumnoId;
        this.firmaAlumno = firmaAlumno;
        this.firmaProf = firmaProf;
        this.firmaTutor = firmaTutor;
        this.horas = horas;
        this.fecha = fecha;
        this.ciclo = ciclo;
    }

    protected Ficha(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        descripcion = in.readString();
        observaciones = in.readString();
        if (in.readByte() == 0) {
            alumnoId = null;
        } else {
            alumnoId = in.readLong();
        }
        firmaAlumno = in.readByte() != 0;
        firmaProf = in.readByte() != 0;
        firmaTutor = in.readByte() != 0;
        horas = in.readInt();
        fecha = in.readString();
        ciclo = in.readString();
    }

    public static final Creator<Ficha> CREATOR = new Creator<Ficha>() {
        @Override
        public Ficha createFromParcel(Parcel in) {
            return new Ficha(in);
        }

        @Override
        public Ficha[] newArray(int size) {
            return new Ficha[size];
        }
    };

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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(descripcion);
        dest.writeString(observaciones);
        if (alumnoId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(alumnoId);
        }
        dest.writeByte((byte) (firmaAlumno ? 1 : 0));
        dest.writeByte((byte) (firmaProf ? 1 : 0));
        dest.writeByte((byte) (firmaTutor ? 1 : 0));
        dest.writeInt(horas);
        dest.writeString(fecha);
        dest.writeString(ciclo);
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }
}
