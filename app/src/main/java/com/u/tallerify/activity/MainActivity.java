package com.u.tallerify.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.RouterTransaction;
import com.u.tallerify.R;
import com.u.tallerify.controller.splash.SplashController;
import com.u.tallerify.utils.RouterInteractor;

/**
 * Created by saguilera on 3/12/17.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.activity_main_toolbar));

        RouterInteractor.instance().attachMainRouter(this,
            (ViewGroup) findViewById(R.id.activity_main_container),
            savedInstanceState);

        RouterInteractor.instance().attachAuxiliaryRouter(this,
            (ViewGroup) findViewById(R.id.activity_dialog_container),
            savedInstanceState);

        if (!RouterInteractor.instance().mainRouter().hasRootController()) {
            RouterInteractor.instance().mainRouter().setRoot(RouterTransaction.with(new SplashController()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!RouterInteractor.instance().auxRouter().handleBack() &&
            !RouterInteractor.instance().mainRouter().handleBack()) {
            super.onBackPressed();
        }
    }

}
