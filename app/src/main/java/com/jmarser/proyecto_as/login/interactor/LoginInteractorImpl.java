package com.jmarser.proyecto_as.login.interactor;


import com.jmarser.proyecto_as.api.WebService;
import com.jmarser.proyecto_as.api.WsApi;
import com.jmarser.proyecto_as.model.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginInteractorImpl implements LoginInteractor {


    @Override
    public void tryToLogin(String email, String password, onLoginFinishedListener listener) {

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
                } else if (response.code() == 409) {//usuario dado de baja
                    listener.enabledUser(response.message());
                } else if (response.code() == 404){//error de login
                    listener.errorUser(response.message());
                }else{
                    listener.unknowError("Fallo desconocido");
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                listener.unknowError("Fallo desconocido");
            }
        });
    }
}
