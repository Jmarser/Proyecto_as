package com.jmarser.proyecto_as.api;

import com.jmarser.proyecto_as.utils.Constantes;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase con patron singleton que crea una instancia de retrofit para toda nuestra app,
 * se encuentra optimizada en el caso de que nuestra app realice llamadas a diferentes api's
 * */

public class WebService {

    private static WebService instance;
    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient.Builder httpClientBuilder;


    private WebService() {
        loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL_TERMINAL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /*Método que nos crea la instancia para usar en nuestra app*/
    public static synchronized WebService getInstance(){
        if(instance == null){
            instance = new WebService();
        }
        return instance;
    }

    /*Método genérico que nos devuelve la conexion con la api que necesitemos en cada caso*/
    public <S> S createWsApi(Class<S> serviceClass){
        return retrofit.create(serviceClass);
    }
}
