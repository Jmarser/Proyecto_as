package com.jmarser.proyecto_as.splash.interactor;

import android.content.Context;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.api.WebService;
import com.jmarser.proyecto_as.api.WsApi;
import com.jmarser.proyecto_as.model.Alumno;
import com.jmarser.proyecto_as.model.Login;
import com.jmarser.proyecto_as.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashInteractorImpl implements SplashInteractor{

    @Override
    public void tryToLogin(String email, String password, onLoginFinishedListener listener, Context context) {
        Login login = new Login();
        login.setEmail(email);
        login.setPassword(password);

        /*hacemos una llamada a nuestro webService genérico con patron singleton y en la misma linea
         * llamamos al método que necesitamos, en este caso login*/
        Call<Login> call = WebService.getInstance().createWsApi(WsApi.class).login(login);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.code() == 200) {//login correcto
                    listener.success(response.body());
                } else if (response.code() == 404) {
                    listener.errorUser(context.getResources().getString(R.string.unregistered_user));
                } else if(response.code() ==409) {
                    listener.enabledUser("");
                }else{
                    listener.unknownError(context.getResources().getString(R.string.unknown_error));
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                listener.unknownError(context.getResources().getString(R.string.unknown_connection_failure));
            }
        });
    }
}
