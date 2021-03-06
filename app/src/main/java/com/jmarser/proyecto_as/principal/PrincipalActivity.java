package com.jmarser.proyecto_as.principal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.alumnos.view.AlumnosFragment;
import com.jmarser.proyecto_as.cuenta.view.CuentaFragment;
import com.jmarser.proyecto_as.errorUsuario.ErrorUsuarioFragment;
import com.jmarser.proyecto_as.fichasAlumno.view.FichasAlumnoFragment;
import com.jmarser.proyecto_as.generarPDF.presenter.GenerarPDFPresenter;
import com.jmarser.proyecto_as.generarPDF.view.GenerarPDFFragment;
import com.jmarser.proyecto_as.login.view.LoginActivity;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;
import com.jmarser.proyecto_as.utils.Constantes;
import com.jmarser.proyecto_as.utils.NavigationActivitis;
import com.jmarser.proyecto_as.utils.NavigationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class PrincipalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar_base)
    Toolbar toolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView nav_view;

    //para poner el icono de hamburguesa al NavigationDrawer
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        //iniciamos con el fragmento correspondiente al usuario logado
        initFragment(SharedPrefManager.getInstance(this).getUsuario().getRol());

        nav_view.bringToFront();//nos aseguramos que el navigationDrawer se muestre al frente

        //funcionalidad para el boton de hamburguesa
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        drawerLayout.addDrawerListener(toggle);//asignamos el boton hamburguesa al drawer

        nav_view.setNavigationItemSelectedListener(this);//funcionalidad a los botones del menu lateral

        //en el caso de que se gire la pantalla volvemos al fragment principal
        if (savedInstanceState == null) {
            initFragment(SharedPrefManager.getInstance(this).getUsuario().getRol());
            nav_view.setCheckedItem(R.id.menu_home);
        }
    }

    /*M??todo que nos dirigir?? al fragment que le corresponda al usuario logado segun su rol*/
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
        } else {
            NavigationFragment.addFragment(getSupportFragmentManager(), ErrorUsuarioFragment.newInstance(), ErrorUsuarioFragment.class.getName());
        }
    }

    /*M??todo que realiza el cierre de sesi??n de la aplicaci??n limpiando el contenido de la sharedPreferences
     * y devolviendos a la actividad de login*/
    private void logout() {
        final AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getResources().getString(R.string.salir))//salir
                .setMessage(getResources().getString(R.string.conf_cerrar_sesion))//"??Seguro que quiere cerrar sesi??n?"
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_home:
                initFragment(SharedPrefManager.getInstance(this).getUsuario().getRol());
                break;
            case R.id.menu_cuenta:
                getSupportActionBar().setTitle("Cambiar password");
                NavigationFragment.replaceFragment(getSupportFragmentManager(), CuentaFragment.newInstance(), CuentaFragment.class.getName());
                break;
            case R.id.menu_desarrollador:
                getSupportActionBar().setTitle("Acerca de...");
                NavigationFragment.replaceFragment(getSupportFragmentManager(), AcercaDeFragment.newInstance(), AcercaDeFragment.class.getName());
                break;
            case R.id.menu_pdf:
                /*Solo el alumno puede generar el PDF, por lo que hacemos una comprobaci??n*/
                if (SharedPrefManager.getInstance(this).getUsuario().getRol().equalsIgnoreCase(Constantes.ROL_ALUMNO)) {
                    if(validarPermisos()){
                        NavigationFragment.addFragment(getSupportFragmentManager(), GenerarPDFFragment.newInstance(),GenerarPDFFragment.class.getName());
                    }
                } else {
                    Toasty.error(this, "No puede generar el PDF", Toasty.LENGTH_SHORT).show();
                }
                break;
            case R.id.menu_logout:
                logout();
                break;
        }

        /*Para que el NavigationDrawer se oculte al pulsar sobre una de las opciones*/
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    private boolean validarPermisos() {
        /*comprobamos la version del android, si es menor de M, no hace falta darle permisos basta
        solo con el permiso del manifiesto*/
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        //comprobamos si ya tenemos permiso de escritura
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            solicitarPermisos();
        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constantes.CODE_WRITE);
        }
        return false;
    }

    private void solicitarPermisos() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Permisos desactivados")
                .setMessage("Debe aceptar los permisos para generar el PDF.")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constantes.CODE_WRITE);
                    }
                });
        dialogo.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constantes.CODE_WRITE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //generamos el PDF
                NavigationFragment.addFragment(getSupportFragmentManager(), GenerarPDFFragment.newInstance(),GenerarPDFFragment.class.getName());
            }
        }
    }
}