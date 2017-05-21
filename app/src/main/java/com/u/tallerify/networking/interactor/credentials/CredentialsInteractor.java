package com.u.tallerify.networking.interactor.credentials;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.networking.AccessTokenManager;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.RestClient;
import com.u.tallerify.networking.interactor.Interactors;
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
public final class CredentialsInteractor {

    public static final int ACTION_LOADING = 0;

    private static @NonNull CredentialsInteractor instance = new CredentialsInteractor();

    @NonNull BehaviorSubject<ReactiveModel<String>> subject;

    private CredentialsInteractor() {
        subject = BehaviorSubject.create();
    }

    public static @NonNull CredentialsInteractor instance() {
        return instance;
    }

    public void dispatchToken(@NonNull String token) {
        subject.onNext(new ReactiveModel.Builder<>()
            .model(token)
            .build());
    }

    public @NonNull Observable<ReactiveModel<String>> observeToken() {
        return subject;
    }

    /**
     * Create POST api. For error handling please subscribe using {@link Interactors#ACTION_NEXT} and
     * {@link Interactors#ACTION_ERROR}. Else subscribe as default.
     *
     * If you need to right away handle something, add actions in the subscribing as you like.
     */
    public @NonNull Observable<String> createWithProvider(final @NonNull Context context, @NonNull CredentialsService.CreateCredentialForm body) {
        return RestClient.with(context)
            .noAuth()
            .create(CredentialsService.class)
            .withProvider(body)
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    subject.onNext(new ReactiveModel.Builder<String>()
                        .action(ACTION_LOADING)
                        .build());
                }
            }).doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    subject.onNext(new ReactiveModel.Builder<String>()
                        .error(throwable)
                        .build());
                }
            }).doOnNext(new Action1<String>() {
                @Override
                public void call(final String accessToken) {
                    AccessTokenManager.instance().write(context, accessToken);
                    subject.onNext(new ReactiveModel.Builder<String>()
                        .model(accessToken)
                        .build());
            }});
    }


    /**
     * Create POST api. For error handling please subscribe using {@link Interactors#ACTION_NEXT} and
     * {@link Interactors#ACTION_ERROR}. Else subscribe as default.
     *
     * If you need to right away handle something, add actions in the subscribing as you like.
     */
    public @NonNull Observable<String> createWithNative(final @NonNull Context context, @NonNull CredentialsService.CreateNativeForm body) {
        return RestClient.with(context)
            .noAuth()
            .create(CredentialsService.class)
            .withNative(body)
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    subject.onNext(new ReactiveModel.Builder<String>()
                        .action(ACTION_LOADING)
                        .build());
                }
            }).doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    subject.onNext(new ReactiveModel.Builder<String>()
                        .error(throwable)
                        .build());
                }
            }).doOnNext(new Action1<String>() {
                @Override
                public void call(final String accessToken) {
                    AccessTokenManager.instance().write(context, accessToken);
                    subject.onNext(new ReactiveModel.Builder<String>()
                        .model(accessToken)
                        .build());
                }});
    }

    /**
     * Refresh POST api. For error handling please subscribe using {@link Interactors#ACTION_NEXT} and
     * {@link Interactors#ACTION_ERROR}. Else subscribe as default.
     *
     * If you need to right away handle something, add actions in the subscribing as you like.
     */
    public @NonNull Observable<String> refresh(final @NonNull Context context, @NonNull CredentialsService.RefreshCredentialForm body) {
        return RestClient.with(context).create(CredentialsService.class)
            .refresh(body)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    subject.onNext(new ReactiveModel.Builder<String>()
                        .action(1) // TODO withProvider loading action
                        .build());
                }
            }).doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    subject.onNext(new ReactiveModel.Builder<String>()
                        .error(throwable)
                        .build());
                }
            }).doOnNext(new Action1<String>() {
                @Override
                public void call(final String accessToken) {
                    AccessTokenManager.instance().write(context, accessToken);
                    subject.onNext(new ReactiveModel.Builder<String>()
                        .model(accessToken)
                        .build());
                }});
    }

}
