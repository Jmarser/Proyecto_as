package com.jmarser.proyecto_as.alumnos.view;

import com.jmarser.proyecto_as.model.Profesor;
import com.jmarser.proyecto_as.model.Tutor;

public interface AlumnosView {

    void setProfesor(Profesor profesor);

    void setTutor(Tutor tutor);

    void errorTutores(String mensaje);

    void unknowError(String mensaje);
}
