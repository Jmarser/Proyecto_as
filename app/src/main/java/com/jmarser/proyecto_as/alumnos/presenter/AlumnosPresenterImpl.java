package com.jmarser.proyecto_as.alumnos.presenter;

import android.content.Context;

import com.jmarser.proyecto_as.alumnos.interactor.AlumnosInteractor;
import com.jmarser.proyecto_as.alumnos.view.AlumnosView;
import com.jmarser.proyecto_as.model.Profesor;
import com.jmarser.proyecto_as.model.Tutor;

public class AlumnosPresenterImpl implements AlumnosPresenter, AlumnosInteractor.OnGetTutoresListener {

    private AlumnosView view;
    private AlumnosInteractor interactor;
    private Context context;

    public AlumnosPresenterImpl(AlumnosView view, AlumnosInteractor interactor, Context context) {
        this.view = view;
        this.interactor = interactor;
        this.context = context;
    }


    @Override
    public void successProfesor(Profesor profesor) {
        view.setProfesor(profesor);
    }

    @Override
    public void succcessTutor(Tutor tutor) {
        view.setTutor(tutor);
    }

    @Override
    public void errorTutores(String mensaje) {
        view.errorTutores(mensaje);
    }

    @Override
    public void unkNowError(String mensaje) {
        view.unknowError(mensaje);
    }

    @Override
    public void getProfesor() {
        interactor.getProfesor(this);
    }

    @Override
    public void getTutor() {
        interactor.getTutor(this);
    }
}
