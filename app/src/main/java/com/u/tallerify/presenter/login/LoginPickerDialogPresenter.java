package com.u.tallerify.presenter.login;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;
import com.facebook.login.LoginResult;
import com.u.tallerify.R;
import com.u.tallerify.contract.login.LoginPickerContract;
import com.u.tallerify.controller.login.LoginNativeDialogController;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.credentials.CredentialsInteractor;
import com.u.tallerify.networking.interactor.facebook.FacebookInteractor;
import com.u.tallerify.presenter.Presenter;
import java.util.Arrays;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/12/17.
 */
public class LoginPickerDialogPresenter extends Presenter<LoginPickerContract.View> implements LoginPickerContract.Presenter {

    @Override
    protected void onAttach(@NonNull final LoginPickerContract.View view) {
        observeView(view);
        observeInteractors(view);
    }

    @Override
    protected void onRender(@NonNull final LoginPickerContract.View view) {}

    private void observeView(final LoginPickerContract.View view) {
        view.observeFacebookLoginClicks()
            .observeOn(Schedulers.computation())
            .subscribeOn(AndroidSchedulers.mainThread())
            .compose(this.<Void>bindToView((View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    FacebookInteractor.instance().loginWithReadPermissions(getAuxiliaryRouter().getActivity(),
                        Arrays.asList(getContext().getResources().getStringArray(R.array.facebook_read_permissions)));
                }
            });

        view.observeNativeLoginClicks()
            .observeOn(Schedulers.computation())
            .subscribeOn(AndroidSchedulers.mainThread())
            .compose(this.<Void>bindToView((View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    showDialog(new LoginNativeDialogController(), LoginNativeDialogController.class.getName());
                }
            });

        view.observeTermsAndConditionsClicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation())
            .compose(this.<Void>bindToView((View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    //TODO getrouter and go to terms and conditions, remove pop up or not ??
                    // Use slide down to dismiss ?
                    Toast.makeText(getContext(), "terms and conditions", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void observeInteractors(final LoginPickerContract.View view) {
        FacebookInteractor.instance().observeFacebookLogins()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation())
            .compose(this.<ReactiveModel<LoginResult>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<LoginResult>>() {
                @Override
                public void call(final ReactiveModel<LoginResult> reactiveModel) {
                    if (reactiveModel.hasError() ||
                        reactiveModel.action() == FacebookInteractor.ACTION_DECLINED_PERMISSIONS) {
                        view.showError();
                    }
                    // If it was cancelled dont do anything
                }
            });

        CredentialsInteractor.instance().observeToken()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation())
            .compose(this.<ReactiveModel<AccessToken>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<AccessToken>>() {
                @Override
                public void call(final ReactiveModel<AccessToken> reactiveModel) {
                    if (reactiveModel.hasError()) {
                        view.showError();
                    }
                }
            });
    }

}
