package com.jmarser.proyecto_as.ficha.interactor;

import com.jmarser.proyecto_as.model.Ficha;

import java.io.File;

public interface FichaFragmentInteractor {

    interface OnUpdateFileListener{

        void successUpdate(Ficha ficha);

        void errorNotFound(String mensaje);

        void unknowError(String mensaje);
    }

    void tryUpdateFile(Ficha ficha, OnUpdateFileListener listener);
}
