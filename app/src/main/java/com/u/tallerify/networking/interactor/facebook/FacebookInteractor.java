package com.u.tallerify.networking.interactor.facebook;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.u.tallerify.networking.ReactiveModel;
import java.util.List;
import rx.Observable;
import rx.subjects.PublishSubject;

import static com.u.tallerify.networking.ReactiveModel.NO_ACTION;

/**
 * Created by saguilera on 3/17/17.
 */
public final class FacebookInteractor {

    public static final int ACTION_CANCELLED = 0;
    public static final int ACTION_DECLINED_PERMISSIONS = 1;

    private static final @NonNull FacebookInteractor instance = new FacebookInteractor();

    private @NonNull CallbackManager facebookCallback;

    @NonNull PublishSubject<ReactiveModel<LoginResult>> tokenSubject;

    @SuppressWarnings("unchecked")
    private FacebookInteractor() {
        tokenSubject = PublishSubject.create();
        facebookCallback = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(facebookCallback,
            new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    tokenSubject.onNext(new ReactiveModel.Builder<LoginResult>()
                        .model(loginResult)
                        .action(loginResult.getAccessToken().getDeclinedPermissions().isEmpty() ?
                            NO_ACTION : ACTION_DECLINED_PERMISSIONS)
                        .build());
                }

                @Override
                public void onCancel() {
                    tokenSubject.onNext(new ReactiveModel.Builder<LoginResult>()
                        .action(ACTION_CANCELLED)
                        .build());
                }

                @Override
                public void onError(FacebookException exception) {
                    tokenSubject.onNext(new ReactiveModel.Builder<LoginResult>()
                        .error(exception)
                        .build());
                }
            });
    }

    public static @NonNull FacebookInteractor instance() {
        return instance;
    }

    public void loginWithReadPermissions(@NonNull Activity activity, @NonNull List<String> permissions) {
        LoginManager.getInstance().logInWithReadPermissions(activity, permissions);
    }

    /**
     * The activity should call this to ensure the callbacks gets called.
     *
     * Facebook sdk smells so good as usual android stuff
     */
    public void postFacebookResults(int requestCode, int resultCode, @NonNull Intent data) {
        facebookCallback.onActivityResult(requestCode, resultCode, data);
    }

    public @NonNull Observable<ReactiveModel<LoginResult>> observeFacebookLogins() {
        return tokenSubject;
    }

}
