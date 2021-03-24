package com.jmarser.proyecto_as.principal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.alumnos.view.AlumnosFragment;
import com.jmarser.proyecto_as.errorUsuario.ErrorUsuarioFragment;
import com.jmarser.proyecto_as.fichasAlumno.view.FichasAlumnoFragment;
import com.jmarser.proyecto_as.login.view.LoginActivity;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;
import com.jmarser.proyecto_as.utils.Constantes;
import com.jmarser.proyecto_as.utils.NavigationActivitis;
import com.jmarser.proyecto_as.utils.NavigationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrincipalActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_base)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initFragment(SharedPrefManager.getInstance(this).getUsuario().getRol());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                logout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*Método que nos dirigirá al fragment que le corresponda al usuario logado segun su rol*/
    private void initFragment(String rol) {
        if (rol.equalsIgnoreCase(Constantes.ROL_ALUMNO)) {
            getSupportActionBar().setTitle(Constantes.ROL_ALUMNO.toUpperCase());
            NavigationFragment.addFragment(getSupportFragmentManager(), FichasAlumnoFragment.newInstance(null), FichasAlumnoFragment.class.getName());
        } else if (rol.equalsIgnoreCase(Constantes.ROL_PROFESOR)) {
            getSupportActionBar().setTitle(Constantes.ROL_PROFESOR.toUpperCase());
            NavigationFragment.addFragment(getSupportFragmentManager(), AlumnosFragment.newInstance(), AlumnosFragment.class.getName());
        } else if (rol.equalsIgnoreCase(Constantes.ROL_TUTOR)) {
            getSupportActionBar().setTitle(Constantes.ROL_TUTOR.toUpperCase());
            NavigationFragment.addFragment(getSupportFragmentManager(), AlumnosFragment.newInstance(), AlumnosFragment.class.getName());
        }else{
            NavigationFragment.addFragment(getSupportFragmentManager(), ErrorUsuarioFragment.newInstance(), ErrorUsuarioFragment.class.getName());
        }
    }

    /*Método que realiza el cierre de sesión de la aplicación limpiando el contenido de la sharedPreferences
    * y devolviendos a la actividad de login*/
    private void logout() {
        final AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getResources().getString(R.string.salir))//salir
                .setMessage(getResources().getString(R.string.conf_cerrar_sesion))//"¿Seguro que quiere cerrar sesión?"
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefManager.getInstance(getApplicationContext()).limpiarShared();
                        NavigationActivitis.pasarActividad(PrincipalActivity.this, LoginActivity.class);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.No), null)
                .show();
    }
}