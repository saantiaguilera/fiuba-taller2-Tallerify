package com.u.tallerify.controller.profile;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.R;
import com.u.tallerify.controller.FlowController;
import com.u.tallerify.presenter.profile.UserInfoPresenter;

/**
 * Created by saguilera on 3/30/17.
 */

public class ProfileController extends FlowController {

    @NonNull
    @Override
    protected View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup container) {
        return inflater.inflate(R.layout.controller_profile, container, false);
    }

    @Override
    protected void onAttach(@NonNull final View view) {
        super.onAttach(view);

        Coordinators.bind(view.findViewById(R.id.controller_profile_info), new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                return new UserInfoPresenter();
            }
        });
    }

    @Nullable
    @Override
    protected String title() {
        return getResources().getString(R.string.toolbar_profile);
    }

}
