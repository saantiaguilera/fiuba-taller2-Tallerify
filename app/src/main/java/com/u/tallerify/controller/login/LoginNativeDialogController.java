package com.u.tallerify.controller.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.R;
import com.u.tallerify.controller.abstracts.AlertDialogController;
import com.u.tallerify.presenter.abstracts.BaseDialogPresenter;
import com.u.tallerify.presenter.login.LoginNativeDialogPresenter;
import com.u.tallerify.view.login.LoginNativeDialogView;

/**
 * Created by saguilera on 4/13/17.
 */

public class LoginNativeDialogController extends AlertDialogController {
    @NonNull
    @Override
    protected View content() {
        View view = new LoginNativeDialogView(getActivity());
        Coordinators.bind(view, new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                return new LoginNativeDialogPresenter();
            }
        });

        return view;
    }

    @NonNull
    @Override
    protected String title() { // TOOD remove constant
        return "Accede mediante " + getResources().getString(R.string.app_name);
    }

    @NonNull
    @Override
    protected BaseDialogPresenter.Severity severity() {
        return BaseDialogPresenter.Severity.INFO;
    }
}
