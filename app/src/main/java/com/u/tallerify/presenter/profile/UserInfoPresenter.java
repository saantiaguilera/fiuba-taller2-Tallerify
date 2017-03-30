package com.u.tallerify.presenter.profile;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.profile.UserInfoContract;
import com.u.tallerify.presenter.Presenter;

/**
 * Created by saguilera on 3/30/17.
 */
public class UserInfoPresenter extends Presenter<UserInfoContract.View>
        implements UserInfoContract.Presenter {

    @Override
    protected void onAttach(@NonNull final UserInfoContract.View view) {
        view.setUserImage("http://placehold.it/320x320");
        view.setUserLastLocation("FIUBA - Paseo Colon 850");
        view.setUserName("Santi Aguilera");
        // TODO Remove mocks
    }

}
