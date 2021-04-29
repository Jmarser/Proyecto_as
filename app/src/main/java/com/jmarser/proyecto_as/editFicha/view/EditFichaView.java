package com.jmarser.proyecto_as.editFicha.view;

public interface EditFichaView {

    void showErrorHoras(String mensaje);

    void showErrorFecha(String mensaje);

    void showErrorDescripcion(String mensaje);

    void showErrorFirma(String mensaje);

    void showErrorEdit(String mensaje);

    void editFichaOk(String mensaje);

    void errorFichaExist(String mensaje);

    void unknowError(String mensaje);

    void serverError(String mensaje);

    void userWithoutAuthorization(String mensaje);
}
