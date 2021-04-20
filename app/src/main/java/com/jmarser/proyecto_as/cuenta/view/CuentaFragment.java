package com.jmarser.proyecto_as.cuenta.view;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.cuenta.interactor.CuentaInteractorImpl;
import com.jmarser.proyecto_as.cuenta.presenter.CuentaPresenter;
import com.jmarser.proyecto_as.cuenta.presenter.CuentaPresenterImpl;
import com.jmarser.proyecto_as.splash.view.SplashActivity;
import com.jmarser.proyecto_as.utils.NavigationActivitis;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class CuentaFragment extends Fragment implements View.OnClickListener, CuentaView {

    @BindView(R.id.til_pass_actual)
    TextInputLayout til_pass_actual;
    @BindView(R.id.til_new_pass)
    TextInputLayout til_new_pass;
    @BindView(R.id.til_newPass_rep)
    TextInputLayout til_newPass_rep;
    @BindView(R.id.btn_cambiar)
    Button btn_cambiar;

    private CuentaPresenter presenter;

    public CuentaFragment() {
        // Required empty public constructor
    }

    public static CuentaFragment newInstance() {
        CuentaFragment fragment = new CuentaFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new CuentaPresenterImpl(getContext(), this, new CuentaInteractorImpl());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cuenta, container, false);
        ButterKnife.bind(this, view);

        btn_cambiar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cambiar:
                presenter.validarCampos(til_pass_actual, til_new_pass, til_newPass_rep);
                break;
        }
    }

    @Override
    public void errorEqualsPass(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void infoEqualsPass(String mensaje) {
        Toasty.info(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void changePassOk(String mensaje) {
        Toasty.success(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
        limpiarCampos();
        NavigationActivitis.pasarActividad(getContext(), SplashActivity.class);
    }

    @Override
    public void UnknowCuenta(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void unknowError(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    private void limpiarCampos(){
        til_pass_actual.getEditText().setText("");
        til_new_pass.getEditText().setText("");
        til_newPass_rep.getEditText().setText("");
    }
}