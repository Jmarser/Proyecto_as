package com.jmarser.proyecto_as.alumnos.interactor;

import com.jmarser.proyecto_as.model.Profesor;
import com.jmarser.proyecto_as.model.Tutor;

public interface AlumnosInteractor {

    interface OnGetTutoresListener {
        void successProfesor(Profesor profesor);

        void succcessTutor(Tutor tutor);

        void errorTutores(String mensaje);

        void unkNowError(String mensaje);
    }

    void getProfesor(OnGetTutoresListener listener);

    void getTutor(OnGetTutoresListener listener);
}
