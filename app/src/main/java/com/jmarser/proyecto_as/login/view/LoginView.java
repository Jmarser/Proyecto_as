package com.jmarser.proyecto_as.login.view;

public interface LoginView {

    void showErrorEmail(String mensaje);

    void showErrorPassword(String mensaje);

    void showErrorUser(String mensaje);

    void goToView(String rol);

    void unknowError(String mensaje);

    void enabledUser(String mensaje);

    void serverError(String mensaje);

    void userWithoutAuthorization(String mensaje);
}
