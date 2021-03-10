package com.jmarser.proyecto_as.api;

import com.jmarser.proyecto_as.model.Alumno;
import com.jmarser.proyecto_as.model.Login;
import com.jmarser.proyecto_as.utils.Constantes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * interface que nos provee de las diferentes llamadas a la api que necesitemos, esta interface se la
 * tendremos que pasar a nuestra clase WebService para que pueda acceder a estas llamadas.*/

public interface WsApi {

    //Llamada POST para realizar el login del usuario de la app.
    @POST(Constantes.API + Constantes.ENDPOINT_LOGIN_USUARIO)
    Call<Login> login(@Body Login login);

    //Llamada POST con la buscaremos un alumno en la tabla alumnos
    @POST(Constantes.API + Constantes.ENDPOINT_GET_ALUMNO)
    Call<Alumno> getAlumno(@Body Alumno alumno);
}
