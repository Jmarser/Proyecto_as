package com.jmarser.proyecto_as.editFicha.interactor;

import com.jmarser.proyecto_as.model.Ficha;

public interface EditFichaInteractor {

    interface OnEditFichaInteractor{
        void success();

        void errorEditFicha(String mensaje);

        void errorFichaExist(String mensaje);

        void unknowError(String mensaje);
    }

    void EditFicha(Ficha ficha, OnEditFichaInteractor listener);
}
