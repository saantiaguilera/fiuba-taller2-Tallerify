package com.u.tallerify.presenter.home.cards;

import android.support.annotation.NonNull;
import android.view.View;
import com.u.tallerify.contract.home.cards.NoAccountCardContract;
import com.u.tallerify.controller.login.LoginDialogController;
import com.u.tallerify.list.adapter.GenericAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by saguilera on 3/12/17.
 */
public class NoAccountCardPresenter extends GenericAdapter.ItemPresenter<NoAccountCardContract.View>
        implements NoAccountCardContract.Presenter {

    @Override
    protected void onAttach(@NonNull final NoAccountCardContract.View view) {
        view.observeOnCreateAccountClick()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(this.<Void>bindToLifecycle((View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    showDialog(new LoginDialogController());
                }
            });
    }

}
