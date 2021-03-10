package com.jmarser.proyecto_as.mySharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.jmarser.proyecto_as.model.Alumno;
import com.jmarser.proyecto_as.model.Login;
import com.jmarser.proyecto_as.model.Usuario;
import com.jmarser.proyecto_as.utils.Constantes;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * clase con patrón singleton con la que creamos una única instancia de SharedPreferences para nuestra
 * aplicación.
 **/
public class SharedPrefManager {

    private static SharedPrefManager instance;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPrefManager(Context context) {
        this.context = context;
        try {
            String masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            this.sharedPreferences = EncryptedSharedPreferences.create(
                    Constantes.SHARED_PREFERENCES,
                    masterKeys,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            editor = sharedPreferences.edit();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

    }

    /*Método que nos devuelve la instancia de nuestra shared. es sincronizado para evitar que si
     * se hacen dos peticiones una vaya detras de la otra, con lo que evitamos posibles fallos*/
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    /*Método con el que salvamos el usuario en la sharedpreferences
     * lo hemos encryptado para dotar a la app de una capa de seguridad*/
    public void guardarUsuario(Login login) {

        editor.putLong(Constantes.SHARED_PREFERENCES_ID, login.getId());
        editor.putString(Constantes.SHARED_PREFERENCES_EMAIL, login.getEmail());
        editor.putString(Constantes.SHARED_PREFERENCES_PASSWORD, login.getPassword());
        editor.putString(Constantes.SHARED_PREFERENCES_ROL , login.getRol());
        editor.apply();

        /* SharedPreferences sin encriptar

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong(Constantes.SHARED_PREFERENCES_ID, alumno.getId());
        editor.putString(Constantes.SHARED_PREFERENCES_NOMBRE, alumno.getNombre());
        editor.putString(Constantes.SHARED_PREFERENCES_EMAIL, alumno.getEmail());
        editor.putString(Constantes.SHARED_PREFERENCES_PASSWORD, alumno.getPassword());
        editor.apply();
        */
    }

    /*Método con el que obtenemos los datos del usuario que se ha guardado en la SharedPreferences*/
    public Login getUsuario() {
        Login login = new Login(
                sharedPreferences.getLong(Constantes.SHARED_PREFERENCES_ID, -1),
                sharedPreferences.getString(Constantes.SHARED_PREFERENCES_EMAIL, null),
                sharedPreferences.getString(Constantes.SHARED_PREFERENCES_PASSWORD, null),
                sharedPreferences.getString(Constantes.SHARED_PREFERENCES_ROL, null)
        );
        return login;
    }

    /*Método que comprueba si hay datos guardados en la sharedPreferences*/
    public boolean isLogin() {

        if (sharedPreferences.getLong(Constantes.SHARED_PREFERENCES_ID, -1) != -1) {
            return true;
        } else {
            return false;
        }
    }

    /*Método que limpia la SharedPreferences*/
    public void limpiarShared() {
        editor.clear().apply();
    }
}
