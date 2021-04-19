package com.jmarser.proyecto_as.editFicha.interactor;

import com.jmarser.proyecto_as.api.WebService;
import com.jmarser.proyecto_as.api.WsApi;
import com.jmarser.proyecto_as.model.Ficha;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditFichaInteractorImpl implements EditFichaInteractor{

    @Override
    public void EditFicha(Ficha ficha, OnEditFichaInteractor listener) {

        Call<Ficha> call = WebService.getInstance().createWsApi(WsApi.class).firmarFicha(ficha.getId(), ficha);
        call.enqueue(new Callback<Ficha>() {
            @Override
            public void onResponse(Call<Ficha> call, Response<Ficha> response) {
                if(response.code() == 200){
                    listener.success();
                }else if(response.code() == 404){
                    listener.errorEditFicha(response.message());
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
