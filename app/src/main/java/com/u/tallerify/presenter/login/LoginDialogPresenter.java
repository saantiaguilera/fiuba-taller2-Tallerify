package com.u.tallerify.presenter.login;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;
import com.bluelinelabs.conductor.RouterTransaction;
import com.u.tallerify.contract.login.LoginContract;
import com.u.tallerify.controller.home.HomeController;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.presenter.Presenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/12/17.
 */
public class LoginDialogPresenter extends Presenter<LoginContract.View> implements LoginContract.Presenter {

    @Override
    protected void onAttach(@NonNull final LoginContract.View view) {
        view.observeFacebookLoginClicks()
            .observeOn(Schedulers.newThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .compose(this.<Void>bindToView((View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    //TODO get access token from fb.. and that stuff.
                    notifyLogin(null);
                }
            });

        view.observeTermsAndConditionsClicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
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

    void notifyLogin(@NonNull AccessToken.Provider login) {
        //TODO Do the login... Meanwhile we go to the home to keep the testings
        // Most probably this will be handled by the controller, since we only want
        // 1 of the observables to get called at a time.
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                getAuxiliaryRouter().popCurrentController();
                getMainRouter().pushController(RouterTransaction.with(new HomeController()));
            }
        });
    }

}
