package com.jmarser.proyecto_as.generarPDF.interactor;

import com.jmarser.proyecto_as.fichasAlumno.interactor.FichasAlumnoInteractor;
import com.jmarser.proyecto_as.model.Alumno;

public interface GenerarPDFInteractor {
    interface OnGetAlumnoListener{
        void success(Alumno alumno);

        void errorAlumno(String mensaje);

        void unknowError(String mensaje);

        void serverError(String mensaje);

        void userWithoutAuthorization(String mensaje);
    }

    void getAlumno(GenerarPDFInteractor.OnGetAlumnoListener listener);
}
