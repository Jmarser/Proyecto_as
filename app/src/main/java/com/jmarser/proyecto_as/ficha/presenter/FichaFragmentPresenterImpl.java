package com.jmarser.proyecto_as.ficha.presenter;

import android.content.Context;

import com.jmarser.proyecto_as.ficha.interactor.FichaFragmentInteractor;
import com.jmarser.proyecto_as.ficha.view.FichaFragmentView;
import com.jmarser.proyecto_as.model.Ficha;

public class FichaFragmentPresenterImpl implements FichaFragmentPresenter, FichaFragmentInteractor.OnUpdateFileListener {

    private Context contexto;
    private FichaFragmentView view;
    private FichaFragmentInteractor interactor;

    public FichaFragmentPresenterImpl(Context contexto, FichaFragmentView view, FichaFragmentInteractor interactor) {
        this.contexto = contexto;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void successUpdate(Ficha ficha) {
        view.successUpdate(ficha);
    }

    @Override
    public void errorNotFound(String mensaje) {
        view.errorFichaId(mensaje);
    }

    @Override
    public void unknowError(String mensaje) {
        view.unknowError(mensaje);
    }

    @Override
    public void checkTeacherSignature(Ficha ficha) {
        if(!ficha.isFirmaProf()){
            ficha.setFirmaProf(true);
            interactor.tryUpdateFile(ficha, this);
        }else{
            view.fileSigned("La ficha ya esta firmada.");
        }
    }

    @Override
    public void checkTutorSignature(Ficha ficha) {
        if(!ficha.isFirmaTutor()){
            ficha.setFirmaTutor(true);
            interactor.tryUpdateFile(ficha, this);
        }else{
            view.fileSigned("La ficha ya esta firmada.");
        }
    }
}
