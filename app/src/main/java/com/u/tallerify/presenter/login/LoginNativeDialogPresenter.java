package com.u.tallerify.presenter.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.u.tallerify.contract.login.LoginNativeContract;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.credentials.CredentialsInteractor;
import com.u.tallerify.networking.interactor.location.LocationInteractor;
import com.u.tallerify.networking.interactor.user.UserInteractor;
import com.u.tallerify.networking.services.credentials.CredentialsService;
import com.u.tallerify.presenter.Presenter;
import java.util.Collections;
import java.util.Date;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 4/13/17.
 */
public class LoginNativeDialogPresenter extends Presenter<LoginNativeContract.View>
        implements LoginNativeContract.Presenter {

    private static final String ADDRESS_SEPARATOR = ", ";

    @Nullable String country;

    @Nullable Bundle params;

    @Nullable String imageUrl;

    @NonNull PublishSubject<Boolean> signupVisibilityChangesSubject = PublishSubject.create();

    @Override
    protected void onAttach(@NonNull final LoginNativeContract.View view) {
        super.onAttach(view);

        view.observeSignUpVisibilityChanges()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<Boolean>bindToLifecycle())
            .filter(new Func1<Boolean, Boolean>() {
                @Override
                public Boolean call(final Boolean signupVisible) {
                    signupVisibilityChangesSubject.onNext(signupVisible);
                    return signupVisible && country == null;
                }
            })
            .flatMap(new Func1<Boolean, Observable<String>>() {
                @Override
                public Observable<String> call(final Boolean aBoolean) {
                    return LocationInteractor.instance().observeLocations();
                }
            })
            .subscribe(new Action1<String>() {
                @Override
                public void call(final String s) {
                    if (s != null) {
                        country = s.substring(s.lastIndexOf(ADDRESS_SEPARATOR) + ADDRESS_SEPARATOR.length());
                        requestRender();
                    }
                }
            });

        view.observeLoginClicks()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<Bundle>bindToLifecycle())
            .filter(new Func1<Bundle, Boolean>() {
                @Override
                public Boolean call(final Bundle bundle) {
                    return params == null;
                }
            })
            .doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    params = null;
                }
            })
            .subscribe(new Action1<Bundle>() {
                @Override
                public void call(final Bundle bundle) {
                    params = bundle;
                    login();
                }
            });

        view.observeSignUpClicks()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<Bundle>bindToLifecycle())
            .filter(new Func1<Bundle, Boolean>() {
                @Override
                public Boolean call(final Bundle bundle) {
                    return params == null && imageUrl != null;
                }
            })
            .doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    params = null;
                }
            })
            .subscribe(new Action1<Bundle>() {
                @Override
                public void call(final Bundle bundle) {
                    params = bundle; // TODO for security measures, password should be encrypted or saved in a secure storage
                    UserInteractor.instance()
                        .create(
                            getContext(),
                            new User.Builder()
                                .name(bundle.getString(LoginNativeContract.KEY_USERNAME))
                                .firstName(bundle.getString(LoginNativeContract.KEY_FIRSTNAME))
                                .lastName(bundle.getString(LoginNativeContract.KEY_LASTNAME))
                                .email(bundle.getString(LoginNativeContract.KEY_EMAIL))
                                .birthday((Date) bundle.get(LoginNativeContract.KEY_BIRTHDAY))
                                .country(bundle.getString(LoginNativeContract.KEY_COUNTRY))
                                .pictures(Collections.singletonList(imageUrl))
                                .id(0)
                                .build(),
                            bundle.getString(LoginNativeContract.KEY_PASSWORD)
                        )
                        .doOnError(new Action1<Throwable>() {
                            @Override
                            public void call(final Throwable throwable) {
                                params = null;
                            }
                        })
                        .subscribe(new Action1<User>() {
                            @Override
                            public void call(final User user) {
                                login();
                            }
                        }, Interactors.ACTION_ERROR);
                }
            }, Interactors.ACTION_ERROR);
    }

    void login() {
        CredentialsInteractor.instance()
            .createWithNative(
                getContext(),
                new CredentialsService.CreateNativeForm(
                    params.getString(LoginNativeContract.KEY_USERNAME),
                    params.getString(LoginNativeContract.KEY_PASSWORD)
                )
            )
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(LoginNativeDialogPresenter.this.<AccessToken>bindToLifecycle())
            .doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    params = null;
                }
            })
            .doOnNext(new Action1<AccessToken>() {
                @Override
                public void call(final AccessToken accessToken) {
                    params = null;
                }
            })
            .subscribe(Interactors.ACTION_NEXT, Interactors.ACTION_ERROR);
    }

    @Override
    protected void onRender(@NonNull final LoginNativeContract.View view) {
        if (country != null) {
            view.suggestCountry(country);
        }
    }

    @Override
    public void setImage(@NonNull final String imageUri) {
        this.imageUrl = imageUri;
    }

    @NonNull
    @Override
    public Observable<Boolean> observeSignupVisibilityChanges() {
        return signupVisibilityChangesSubject;
    }

}
