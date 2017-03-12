package com.u.tallerify.controller.login;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.view.View;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.R;
import com.u.tallerify.controller.abstracts.BaseDialogController;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.credentials.CredentialsInteractor;
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
@SuppressLint("ValidController")
public class LoginDialogController extends BaseDialogController {

    public LoginDialogController() {
        super(BaseDialogPresenter.Severity.WARNING,
            RouterInteractor.instance().mainRouter()
                .getActivity().getResources().getString(R.string.login_dialog_title),
            new LoginDialogView(RouterInteractor.instance().mainRouter().getActivity()),
            true);

        Coordinators.bind(content(), new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                return new LoginDialogPresenter();
            }
        });

        CredentialsInteractor.instance().observeToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<AccessToken>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<AccessToken>>() {
                @Override
                public void call(final ReactiveModel<AccessToken> accessTokenReactiveModel) {
                    // TODO Do stuff.
                }
            });
    }

}
