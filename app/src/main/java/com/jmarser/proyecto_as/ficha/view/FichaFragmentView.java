package com.jmarser.proyecto_as.ficha.view;


import com.jmarser.proyecto_as.model.Ficha;

public interface FichaFragmentView {

    void unknowError(String mensaje);

    void errorFichaId(String mensaje);

    void successUpdate(Ficha ficha);

    void fileSigned(String mensaje);

}
