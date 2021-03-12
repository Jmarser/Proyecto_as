package com.jmarser.proyecto_as.utils;

/**
 * Clase que contiene las diferentes constantes y endPoint que usaremos en nuestra aplicacion*/

public class Constantes {

    /*Constantes MySharedPref*/
    public static final String SHARED_PREFERENCES = "user_preferences";
    public static final String SHARED_PREFERENCES_ID = "user_preferences_id";
    public static final String SHARED_PREFERENCES_EMAIL = "user_preferences_email";
    public static final String SHARED_PREFERENCES_PASSWORD = "user_preferences_password";
    public static final String SHARED_PREFERENCES_ROL = "user_preferences_rol";

    /*Constantes End point Retrofit*/
    public static final String BASE_URL_EMULADOR = "http://10.0.2.2:8080";//usada desde emulador para localhost
    public static final String BASE_URL_TERMINAL = "http://192.168.1.40:8080";//usada desde terminal para localhost
    public static final String API = "/api";
    public static final String ENDPOINT_LOGIN_USUARIO = "/login";
    public static final String ENDPOINT_GET_ALUMNO = "/alumno";
    public static final String ENDPOINT_GET_PROFESOR = "/profesor";
    public static final String ENDPOINT_GET_TUTOR = "/tutor";

    /*Constantes roles*/
    public static final String ROL_ALUMNO = "Alumno";
    public static final String ROL_PROFESOR = "Profesor";
    public static final String ROL_TUTOR = "Tutor";

    /*Horas para cumplir con las practicas*/
    public static final int HORAS_PRACTICAS = 350;



}
