package com.u.tallerify.controller.abstracts;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.controller.DialogController;
import com.u.tallerify.presenter.abstracts.BaseDialogPresenter;
import com.u.tallerify.view.abstracts.BaseDialogView;

/**
 * Created by saguilera on 3/12/17.
 */
public abstract class AlertDialogController extends DialogController {

    @NonNull
    @Override
    protected Dialog onCreateDialog(@NonNull final Context context) {
        View view = new BaseDialogView(getActivity());

        Coordinators.bind(view, new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                return new BaseDialogPresenter.Builder()
                    .severity(severity())
                    .title(title())
                    .content(content())
                    .build();
            }
        });

        return new AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(isCancellable())
            .create();
    }

    @Override
    public boolean handleBack() {
        return !isCancellable() || super.handleBack();
    }


    public boolean isCancellable() {
        return true;
    }

    protected abstract @NonNull View content();
    protected abstract @NonNull String title();
    protected abstract @NonNull BaseDialogPresenter.Severity severity();

}
