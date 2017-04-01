package com.u.tallerify.presenter.profile;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.profile.ProfileUserInfoContract;
import com.u.tallerify.presenter.Presenter;

/**
 * Created by saguilera on 3/30/17.
 */
public class ProfileUserInfoPresenter extends Presenter<ProfileUserInfoContract.View>
        implements ProfileUserInfoContract.Presenter {

    @Override
    protected void onAttach(@NonNull final ProfileUserInfoContract.View view) {
        view.setUserImage("http://placehold.it/320x320");
        view.setUserLastLocation("FIUBA - Paseo Colon 850");
        view.setUserName("Santi Aguilera");
        // TODO Remove mocks
    }

}
