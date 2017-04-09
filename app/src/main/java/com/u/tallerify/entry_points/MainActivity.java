package com.u.tallerify.entry_points;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.RouterTransaction;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.u.tallerify.R;
import com.u.tallerify.controller.FlowController;
import com.u.tallerify.controller.splash.SplashController;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.facebook.FacebookInteractor;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.presenter.base.music_player.MusicPlayerPresenter;
import com.u.tallerify.utils.CurrentPlay;
import com.u.tallerify.utils.RequestCodes;
import com.u.tallerify.utils.RouterInteractor;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/12/17.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setViews(savedInstanceState);

        if (CurrentPlay.instance() == null) {
            observeCurrentPlay();
        }

        observeLogins();

        // Starting flow
        if (!RouterInteractor.instance().mainRouter().hasRootController()) {
            RouterInteractor.instance().mainRouter().setRoot(RouterTransaction.with(new SplashController()));
        }
    }

    private void observeCurrentPlay() {
        // Show music player when current play exists
        CurrentPlay.observeCurrentPlay()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .compose(RxLifecycleAndroid.<CurrentPlay>bindView(findViewById(R.id.activity_main_root)))
            .subscribe(new Action1<CurrentPlay>() {
                @Override
                public void call(final CurrentPlay currentPlay) {
                    // Set the bottom nav play bar
                    List<RouterTransaction> backstack =
                        RouterInteractor.instance().mainRouter().getBackstack();
                    if (!backstack.isEmpty()) {
                        ((FlowController) backstack.get(backstack.size() - 1).controller()).renderMediaPlayer(true);
                    }

                    // Start the play service
                    Intent serviceIntent = new Intent(MainActivity.this, PlayService.class);
                    serviceIntent.setAction(PlayService.SERVICE_START);
                    startService(serviceIntent);
                }
            });
    }

    private void observeLogins() {
        MeInteractor.instance().observeUser()
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .compose(RxLifecycleAndroid.<ReactiveModel<User>>bindView(findViewById(R.id.activity_main_root)))
            .filter(new Func1<ReactiveModel<User>, Boolean>() {
                @Override
                public Boolean call(final ReactiveModel<User> userReactiveModel) {
                    return userReactiveModel.model() != null && !userReactiveModel.hasError();
                }
            })
            .take(1)
            .subscribe(new Action1<ReactiveModel<User>>() {
                @Override
                public void call(final ReactiveModel<User> rxModel) {
                    loginFirebase(rxModel);
                }
            });
    }

    void loginFirebase(@NonNull final ReactiveModel<User> rxModel) {
        if (rxModel.model() != null && !rxModel.hasError()) {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                // No firebase account, login/create
                final User user = rxModel.model();
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        user.email(),
                        user.name() + "_" + user.id()
                    )
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull final Exception e) {
                            // Only check for FirebaseAuthUserCollisionException. weak passwords cant be
                            // triggered since user doesnt interact with this and email shouldnt be
                            // malformed. track it if it is
                            if (e instanceof FirebaseAuthUserCollisionException) {
                                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                                        user.email(),
                                        user.name() + "_" + user.id()
                                    )
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull final Exception e) {
                                            // If something went wrong, "track it" and rerun in 5 seconds again
                                            e.printStackTrace();
                                            Observable.timer(5, TimeUnit.SECONDS)
                                                .subscribe(new Action1<Long>() {
                                                    @Override
                                                    public void call(final Long aLong) {
                                                        loginFirebase(rxModel);
                                                    }
                                                });
                                        }
                                    });
                            }
                        }
                    });
            }
        }
    }

    private void setViews(@Nullable Bundle savedInstanceState) {
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
