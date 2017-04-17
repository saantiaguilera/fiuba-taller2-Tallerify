package com.u.tallerify.presenter.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.u.tallerify.contract.login.LoginNativeContract;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.credentials.CredentialsInteractor;
import com.u.tallerify.networking.services.credentials.CredentialsService;
import com.u.tallerify.presenter.Presenter;
import com.u.tallerify.view.login.LoginNativeDialogView;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 4/13/17.
 */
public class LoginNativeDialogPresenter extends Presenter<LoginNativeContract.View>
        implements LoginNativeContract.Presenter {

    @Override
    protected void onAttach(@NonNull final LoginNativeContract.View view) {
        super.onAttach(view);

        view.observeLoginClicks()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<Bundle>bindToLifecycle())
            .subscribe(new Action1<Bundle>() {
                @Override
                public void call(final Bundle bundle) {
                    CredentialsInteractor.instance()
                        .createWithNative(
                            getContext(),
                            new CredentialsService.CreateNativeForm(
                                bundle.getString(LoginNativeContract.KEY_USERNAME),
                                bundle.getString(LoginNativeContract.KEY_PASSWORD)
                            )
                        )
                        .observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .compose(LoginNativeDialogPresenter.this.<AccessToken>bindToLifecycle())
                        .subscribe(Interactors.ACTION_NEXT, Interactors.ACTION_ERROR);
                }
            });

        view.observeSignUpClicks()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<Bundle>bindToLifecycle())
            .subscribe(new Action1<Bundle>() {
                @Override
                public void call(final Bundle bundle) {
                    // TODO do signup and then login
                }
            });
    }

    @Override
    protected void onRender(@NonNull final LoginNativeContract.View view) {}

}
