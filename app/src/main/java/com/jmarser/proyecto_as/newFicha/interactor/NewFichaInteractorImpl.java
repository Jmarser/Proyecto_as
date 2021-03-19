package com.jmarser.proyecto_as.newFicha.interactor;


import com.jmarser.proyecto_as.api.WebService;
import com.jmarser.proyecto_as.api.WsApi;
import com.jmarser.proyecto_as.model.Ficha;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFichaInteractorImpl implements NewFichaInteractor{
    @Override
    public void saveFicha(Ficha ficha, OnPostNewFichaListener listener) {
        Call<Ficha> call = WebService.getInstance().createWsApi(WsApi.class).saveFicha(ficha);
        call.enqueue(new Callback<Ficha>() {
            @Override
            public void onResponse(Call<Ficha> call, Response<Ficha> response) {
                if(response.code() == 201) {
                    listener.success();
                }else if(response.code() == 409){
                    listener.errorFichaExist(response.message());
                }else if(response.code() == 400){
                    listener.errorNewFicha(response.message());
                }else{
                    listener.unknowError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Ficha> call, Throwable t) {
                listener.unknowError("Error inesperado del servidor.");
            }
        });
    }
}
