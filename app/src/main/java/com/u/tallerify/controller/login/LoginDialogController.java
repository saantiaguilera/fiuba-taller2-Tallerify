package com.u.tallerify.controller.login;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
import com.facebook.login.LoginResult;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.R;
import com.u.tallerify.controller.abstracts.BaseDialogController;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.credentials.CredentialsInteractor;
import com.u.tallerify.networking.interactor.facebook.FacebookInteractor;
import com.u.tallerify.networking.services.credentials.CredentialsService;
import com.u.tallerify.presenter.abstracts.BaseDialogPresenter;
import com.u.tallerify.presenter.login.LoginDialogPresenter;
import com.u.tallerify.utils.RouterInteractor;
import com.u.tallerify.view.login.LoginDialogView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/12/17.
 */
public class LoginDialogController extends BaseDialogController {

    public LoginDialogController() {
        super(BaseDialogPresenter.Severity.WARNING,
            RouterInteractor.instance().mainRouter()
                .getActivity().getResources().getString(R.string.view_dialog_login_title),
            new LoginDialogView(RouterInteractor.instance().mainRouter().getActivity()),
            true);

        Coordinators.bind(content(), new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                return new LoginDialogPresenter();
            }
        });

        // Interact with app tokens
        FacebookInteractor.instance().observeFacebookLogins()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<LoginResult>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<LoginResult>>() {
                @Override
                @SuppressWarnings("ConstantConditions")
                public void call(final ReactiveModel<LoginResult> reactiveModel) {
                    if (reactiveModel.model() != null &&
                            reactiveModel.action() == ReactiveModel.NO_ACTION) {
                        nativeLogin(reactiveModel.model());
                    } // We only care about the ok result, bad results will probably be managed by some presenter for showing in the ui
                }
            });

        CredentialsInteractor.instance().observeToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<AccessToken>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<AccessToken>>() {
                @Override
                public void call(final ReactiveModel<AccessToken> accessTokenReactiveModel) {
                    if (accessTokenReactiveModel.model() != null &&
                            accessTokenReactiveModel.action() == ReactiveModel.NO_ACTION) {
                        getRouter().popCurrentController();
                    }
                }
            });
    }

    void nativeLogin(@NonNull LoginResult result) {
        // TODO remove this, testing purposes
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Native login...", Toast.LENGTH_LONG).show();
            }
        });

        CredentialsInteractor.instance().create(getApplicationContext(),
                new CredentialsService.CreateCredentialForm(
                    result.getAccessToken().getToken(),
                    AccessToken.Provider.FACEBOOK))
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(bindToLifecycle())
            .subscribe();
    }

}
