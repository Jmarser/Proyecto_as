package com.jmarser.proyecto_as.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jmarser.proyecto_as.R;


/**
 * clase que contiene el las instrucciones para reemplazar y adquirir los fragments que vayamos
 * necesitando.
 * con esto evitamos la repetición de cógido en nuestras diferentes clases.*/
public class NavigationFragment {

    public static void replaceFragment(FragmentManager fm, Fragment fragment, String fragmentTag) {
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, 0, 0, android.R.anim.fade_out)
                .replace(R.id.frame, fragment, fragmentTag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public static void addFragment(FragmentManager fm, Fragment fragment, String fragmentTag) {
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, 0, 0, android.R.anim.fade_out)
                .add(R.id.frame, fragment, fragmentTag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
