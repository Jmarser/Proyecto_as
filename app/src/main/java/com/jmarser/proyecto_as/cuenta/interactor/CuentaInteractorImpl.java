package com.jmarser.proyecto_as.cuenta.interactor;

import com.jmarser.proyecto_as.api.WebService;
import com.jmarser.proyecto_as.api.WsApi;
import com.jmarser.proyecto_as.model.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuentaInteractorImpl implements CuentaInteractor{

    @Override
    public void tryChangePassword(Login login, changePasswordListener listener) {

        Call<Login> call = WebService.getInstance().createWsApi(WsApi.class).modificarPass(login.getId(), login);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.code() == 200){
                    listener.success(response.body());
                }else if(response.code() == 404){
                    listener.errorUnknowCuenta("El usuario no esta registrado.");
                }else{
                    listener.unknowError("Error desconocido.");
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                listener.errorUnknowCuenta("Ocurrio un error desconocido.");
            }
        });
    }
}
