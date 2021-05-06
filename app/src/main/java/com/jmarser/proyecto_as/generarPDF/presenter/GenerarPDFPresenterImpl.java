package com.jmarser.proyecto_as.generarPDF.presenter;

import android.content.Context;

import com.jmarser.proyecto_as.generarPDF.interactor.GenerarPDFInteractor;
import com.jmarser.proyecto_as.generarPDF.view.GenerarPDFView;
import com.jmarser.proyecto_as.model.Alumno;

public class GenerarPDFPresenterImpl implements GenerarPDFPresenter, GenerarPDFInteractor.OnGetAlumnoListener {

    private Context context;
    private GenerarPDFInteractor interactor;
    private GenerarPDFView view;

    public GenerarPDFPresenterImpl(Context context, GenerarPDFView view, GenerarPDFInteractor interactor) {
        this.context = context;
        this.interactor = interactor;
        this.view = view;
    }


    @Override
    public void getAlumno() {
        interactor.getAlumno(this);
    }

    @Override
    public void success(Alumno alumno) {
        view.setAlumno(alumno);
    }

    @Override
    public void errorAlumno(String mensaje) {
        view.errorAlumno(mensaje);
    }

    @Override
    public void unknowError(String mensaje) {
        view.unknowError(mensaje);
    }

    @Override
    public void serverError(String mensaje) {
        view.serverError(mensaje);
    }

    @Override
    public void userWithoutAuthorization(String mensaje) {
        view.userWithoutAuthorization(mensaje);
    }

}
