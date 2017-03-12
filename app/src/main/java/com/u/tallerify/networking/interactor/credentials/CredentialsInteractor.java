package com.u.tallerify.networking.interactor.credentials;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.RestClient;
import com.u.tallerify.networking.services.credentials.CredentialsService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * User should be in charge of subscribing and unsubscribing, so each method just setts all the configurations
 * leaving the user just the subscribal. (This way they can either add composables or unsubscribe when they want)
 *
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unchecked")
public class CredentialsInteractor {

    private static final @NonNull CredentialsInteractor instance = new CredentialsInteractor();

    @NonNull BehaviorSubject<ReactiveModel<AccessToken>> tokenBehaviorSubject;

    private CredentialsInteractor() {
        tokenBehaviorSubject = BehaviorSubject.create();
    }

    public static @NonNull CredentialsInteractor instance() {
        return instance;
    }

    public Observable<ReactiveModel<AccessToken>> observeToken() {
        return tokenBehaviorSubject;
    }

    public Observable<?> create(@NonNull Context context, @NonNull CredentialsService.CreateCredentialForm body) {
        return RestClient.with(context).create(CredentialsService.class)
            .create(body)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    tokenBehaviorSubject.onNext(new ReactiveModel.Builder<AccessToken>()
                        .action(1) // TODO create loading action
                        .build());
                }
            }).doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    tokenBehaviorSubject.onNext(new ReactiveModel.Builder<AccessToken>()
                        .error(throwable)
                        .build());
                }
            }).doOnNext(new Action1<AccessToken>() {
                @Override
                public void call(final AccessToken accessToken) {
                    tokenBehaviorSubject.onNext(new ReactiveModel.Builder<AccessToken>()
                        .model(accessToken)
                        .build());
                }});
    }

    public Observable<?> refresh(@NonNull Context context, @NonNull CredentialsService.RefreshCredentialForm body) {
        return RestClient.with(context).create(CredentialsService.class)
            .refresh(body)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    tokenBehaviorSubject.onNext(new ReactiveModel.Builder<AccessToken>()
                        .action(1) // TODO create loading action
                        .build());
                }
            }).doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    tokenBehaviorSubject.onNext(new ReactiveModel.Builder<AccessToken>()
                        .error(throwable)
                        .build());
                }
            }).doOnNext(new Action1<AccessToken>() {
                @Override
                public void call(final AccessToken accessToken) {
                    tokenBehaviorSubject.onNext(new ReactiveModel.Builder<AccessToken>()
                        .model(accessToken)
                        .build());
                }});
    }

}
