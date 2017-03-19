package com.u.tallerify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.RouterTransaction;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.R;
import com.u.tallerify.controller.FlowController;
import com.u.tallerify.controller.splash.SplashController;
import com.u.tallerify.networking.interactor.facebook.FacebookInteractor;
import com.u.tallerify.presenter.base.MusicPlayerPresenter;
import com.u.tallerify.utils.CurrentPlay;
import com.u.tallerify.utils.RequestCodes;
import com.u.tallerify.utils.RouterInteractor;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/12/17.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Content
        setContentView(R.layout.activity_main);

        // Toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.activity_main_toolbar));

        // Main controller router
        RouterInteractor.instance().attachMainRouter(this,
            (ViewGroup) findViewById(R.id.activity_main_container),
            savedInstanceState);

        // Aux controller router
        RouterInteractor.instance().attachAuxiliaryRouter(this,
            (ViewGroup) findViewById(R.id.activity_dialog_container),
            savedInstanceState);

        // Music player view binding
        Coordinators.bind(findViewById(R.id.activity_main_player_view), new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                return new MusicPlayerPresenter();
            }
        });

        // Show music player when current play exists
        CurrentPlay.observeCurrentPlay()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe(new Action1<CurrentPlay>() {
                @Override
                public void call(final CurrentPlay currentPlay) {
                    List<RouterTransaction> backstack =
                        RouterInteractor.instance().mainRouter().getBackstack();
                    if (!backstack.isEmpty()) {
                        ((FlowController) backstack.get(backstack.size() - 1).controller()).renderMediaPlayer(true);
                    }
                }
            });

        // Starting flow
        if (!RouterInteractor.instance().mainRouter().hasRootController()) {
            RouterInteractor.instance().mainRouter().setRoot(RouterTransaction.with(new SplashController()));
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        RequestCodes code = RequestCodes.valueOf(requestCode);

        if (code != null) {
            switch (code) {
                case FACEBOOK_LOGIN:
                    FacebookInteractor.instance().postFacebookResults(requestCode, resultCode, data);
            }
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
