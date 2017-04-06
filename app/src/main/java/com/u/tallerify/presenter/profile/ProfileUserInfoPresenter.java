package com.u.tallerify.presenter.profile;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.profile.ProfileUserInfoContract;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.presenter.Presenter;
import javax.annotation.Nullable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/30/17.
 */
public class ProfileUserInfoPresenter extends Presenter<ProfileUserInfoContract.View>
        implements ProfileUserInfoContract.Presenter {

    @Nullable User me;

    public ProfileUserInfoPresenter() {
        MeInteractor.instance().observeUser()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<User>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<User>>() {
                @Override
                public void call(final ReactiveModel<User> rxModel) {
                    if (rxModel.model() != null && !rxModel.hasError()) {
                        me = rxModel.model();

                        if (isAttached()) {
                            requestView();
                        }
                    }
                }
            })
    }

    @Override
    protected void onAttach(@NonNull final ProfileUserInfoContract.View view) {
        view.setUserLastLocation("FIUBA - Paseo Colon 850");
        if (me != null) {
            setUser(view);
        }
    }

    private void setUser(@NonNull ProfileUserInfoContract.View view) {
        view.setUserName(me.name());
        view.setUserImage(me.pictures().get(0));
    }

}
