package com.jmarser.proyecto_as.cuenta.view;

public interface CuentaView {

    void errorEqualsPass(String mensaje);

    void infoEqualsPass(String mensaje);

    void changePassOk(String mensaje);

    void UnknowCuenta(String mensaje);

    void unknowError(String mensaje);

    void serverError(String mensaje);

    void userWithoutAuthorization(String mensaje);
}
