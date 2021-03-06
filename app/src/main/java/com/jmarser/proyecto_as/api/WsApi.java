package com.jmarser.proyecto_as.api;

import com.jmarser.proyecto_as.model.Alumno;
import com.jmarser.proyecto_as.model.Ficha;
import com.jmarser.proyecto_as.model.Login;
import com.jmarser.proyecto_as.model.Profesor;
import com.jmarser.proyecto_as.model.Tutor;
import com.jmarser.proyecto_as.utils.Constantes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * interface que nos provee de las diferentes llamadas a la api que necesitemos, esta interface se la
 * tendremos que pasar a nuestra clase WebService para que pueda acceder a estas llamadas.*/

public interface WsApi {

    //Llamada POST para realizar el login del usuario de la app.
    @POST(Constantes.API + Constantes.ENDPOINT_LOGIN_USUARIO)
    Call<Login> login(@Header("Authorization")String user, @Body Login login);

    //Llamada POST con la buscaremos un alumno en la tabla alumnos
    @POST(Constantes.API + Constantes.ENDPOINT_GET_ALUMNO)
    Call<Alumno> getAlumno(@Header("Authorization")String user, @Body Alumno alumno);

    //Llamada POST con la que buscaremos un profesor en la tabla correspondiente
    @POST(Constantes.API + Constantes.ENDPOINT_GET_PROFESOR)
    Call<Profesor> getProfesor(@Header("Authorization")String user, @Body Profesor profesor);

    //Llamada POST con la que buscaremos un tutor en la tabla correspondiente
    @POST(Constantes.API + Constantes.ENDPOINT_GET_TUTOR)
    Call<Tutor> getTutor(@Header("Authorization")String user, @Body Tutor tutor);

    //Llamada POST con la que guardaremos una nueva ficha en la tabla
    @POST(Constantes.API + Constantes.ENDPOINT_SAVE_FICHA)
    Call<Ficha> saveFicha(@Header("Authorization")String user, @Body Ficha ficha);

    //Llamada GET con la que obtenemos un alumno de la base de datos por su id
    @GET(Constantes.API + Constantes.ENDPOINT_GET_ALUMNO_ID)
    Call<Alumno> getAlumnoById(@Header("Authorization")String user, @Path("id") Long id);

    //Llamada de actualizaci??n de los datos de una ficha
    @PUT(Constantes.API + Constantes.ENDPOINT_UPDATE_FICHA)
    Call<Ficha> firmarFicha(@Header("Authorization")String user, @Path("id")Long id, @Body Ficha ficha);

    //Llamada de modificaci??n del password del usuario.
    @PUT(Constantes.API + Constantes.ENDPOINT_UPDATE_PASS)
    Call<Login> modificarPass(@Header("Authorization")String user, @Path("id")Long id, @Body Login login);


}
