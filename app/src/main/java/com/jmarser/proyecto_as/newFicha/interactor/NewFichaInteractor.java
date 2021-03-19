package com.jmarser.proyecto_as.newFicha.interactor;

import com.jmarser.proyecto_as.model.Ficha;

public interface NewFichaInteractor {

    interface OnPostNewFichaListener{
        void success();

        void errorNewFicha(String mensaje);

        void errorFichaExist(String mensaje);

        void unknowError(String mensaje);
    }

    void saveFicha(Ficha ficha, OnPostNewFichaListener listener);
}
