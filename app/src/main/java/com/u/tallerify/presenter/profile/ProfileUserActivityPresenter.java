package com.u.tallerify.presenter.profile;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.profile.ProfileUserActivityContract;
import com.u.tallerify.presenter.Presenter;

/**
 * Created by saguilera on 3/30/17.
 */

public class ProfileUserActivityPresenter extends Presenter<ProfileUserActivityContract.View>
        implements ProfileUserActivityContract.Presenter {

    @Override
    protected void onAttach(@NonNull final ProfileUserActivityContract.View view) {
        // view.setActivities(...);
    }

}
