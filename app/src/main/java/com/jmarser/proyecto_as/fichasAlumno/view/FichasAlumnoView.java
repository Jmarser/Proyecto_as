package com.jmarser.proyecto_as.fichasAlumno.view;

import com.jmarser.proyecto_as.model.Alumno;

public interface FichasAlumnoView {

    void setAlumno(Alumno alumno);

    void errorAlumno(String mensaje);

    void unknowError(String mensaje);

    void serverError(String mensaje);

    void userWithoutAuthorization(String mensaje);
}
