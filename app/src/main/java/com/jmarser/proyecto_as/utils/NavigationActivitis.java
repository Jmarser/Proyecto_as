package com.jmarser.proyecto_as.utils;

import android.content.Context;
import android.content.Intent;


/*Clase con la que evitamos la repetición de código en nuestra aplicación*/
public class NavigationActivitis {

    public static void pasarActividad(Context context, Class clase){
        Intent intent = new Intent(context, clase);
        /*para evitar que desde la actividad de destino podamos volver a esta actividad utilizamos los
        siguinetes flags*/
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
