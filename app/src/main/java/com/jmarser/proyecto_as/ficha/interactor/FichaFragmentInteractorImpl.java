package com.jmarser.proyecto_as.ficha.interactor;

import com.jmarser.proyecto_as.api.WebService;
import com.jmarser.proyecto_as.api.WsApi;
import com.jmarser.proyecto_as.model.Ficha;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichaFragmentInteractorImpl implements FichaFragmentInteractor{

    @Override
    public void tryUpdateFile(Ficha ficha, OnUpdateFileListener listener) {
        Call<Ficha> call = WebService.getInstance().createWsApi(WsApi.class).firmarFicha(ficha.getId(), ficha);
        call.enqueue(new Callback<Ficha>() {
            @Override
            public void onResponse(Call<Ficha> call, Response<Ficha> response) {
                if(response.code() == 200){
                    listener.successUpdate(response.body());
                }else if(response.code() == 404){
                    listener.errorNotFound(response.message());
                }else{
                    listener.unknowError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Ficha> call, Throwable t) {
                listener.unknowError("Error desconocido del servidor");
            }
        });
    }
}
