package com.jmarser.proyecto_as.fichasAlumno.presenter;

import android.content.Context;

import com.jmarser.proyecto_as.fichasAlumno.interactor.FichasAlumnoInteractor;
import com.jmarser.proyecto_as.fichasAlumno.view.FichasAlumnoView;
import com.jmarser.proyecto_as.model.Alumno;

public class FichasAlumnoPresenterImpl implements FichasAlumnoPresenter, FichasAlumnoInteractor.OnGetAlumnoListener {

    private FichasAlumnoView view;
    private FichasAlumnoInteractor interactor;
    private Context context;

    public FichasAlumnoPresenterImpl(FichasAlumnoView view, FichasAlumnoInteractor interactor, Context context) {
        this.view = view;
        this.interactor = interactor;
        this.context = context;
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

    @Override
    public void getAlumno() {
        interactor.getAlumno(this);
    }

    @Override
    public void getAlumnoById(Long id) {
        interactor.getAlumnoById(id, this);
    }
}
