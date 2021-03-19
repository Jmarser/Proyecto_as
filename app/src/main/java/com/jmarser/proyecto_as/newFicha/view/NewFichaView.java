package com.jmarser.proyecto_as.newFicha.view;

public interface NewFichaView {

    void showErrorHoras(String mensaje);

    void showErrorFecha(String mensaje);

    void showErrorDescripcion(String mensaje);

    void showErrorFirma(String mensaje);

    void showErrorAlumno(String mensaje);

    void saveFichaOk(String mensaje);

    void errorFichaExist(String mensaje);

    void unknowError(String mensaje);
}
