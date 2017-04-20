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
import com.u.tallerify.contract.abstracts.BaseDialogContract;
import com.u.tallerify.controller.DialogController;
import com.u.tallerify.presenter.abstracts.BaseDialogPresenter;
import com.u.tallerify.view.abstracts.BaseDialogView;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by saguilera on 3/12/17.
 */
public abstract class AlertDialogController extends DialogController {

    BaseDialogContract.Presenter presenter;

    @NonNull
    final PublishSubject<Void> imageClicksSubject = PublishSubject.create();

    @NonNull
    @Override
    protected Dialog onCreateDialog(@NonNull final Context context) {
        View view = new BaseDialogView(getActivity());

        Coordinators.bind(view, new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                observeImageClicks(imageClicksSubject); // When binding presenter provide observable
                return (Coordinator) (presenter = new BaseDialogPresenter.Builder()
                    .severity(severity())
                    .title(title())
                    .content(content())
                    .imageUrl(imageUrl())
                    .imageClicksSubject(imageClicksSubject)
                    .build());
            }
        });

        return new AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(isCancellable())
            .create();
    }

    protected void onImageChange() {
        if (presenter != null) {
            presenter.onImageChange(imageUrl());
        }
    }

    protected void onTitleChange() {
        if (presenter != null) {
            presenter.onTitleChange(title());
        }
    }

    protected void onSeverityChange() {
        if (presenter != null) {
            presenter.onSeverityChange(severity());
        }
    }

    protected void onContentChange() {
        if (presenter != null) {
            presenter.onContentChange(content());
        }
    }

    protected void onDataSetChange() {
        onImageChange();
        onTitleChange();
        onSeverityChange();
        onContentChange();
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
    protected @Nullable String imageUrl() {
        return null;
    }
    protected void observeImageClicks(@NonNull Observable<Void> observable) {}

}
