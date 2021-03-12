package com.jmarser.proyecto_as.fichasAlumno.interactor;

import com.jmarser.proyecto_as.model.Alumno;

public interface FichasAlumnoInteractor {

    interface OnGetAlumnoListener{
        void success(Alumno alumno);

        void errorAlumno(String mensaje);

        void unknowError(String mensaje);
    }

    void getAlumno(OnGetAlumnoListener listener);
}
