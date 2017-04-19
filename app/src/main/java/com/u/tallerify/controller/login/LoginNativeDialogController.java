package com.u.tallerify.controller.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.R;
import com.u.tallerify.controller.abstracts.AlertDialogController;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.credentials.CredentialsInteractor;
import com.u.tallerify.presenter.abstracts.BaseDialogPresenter;
import com.u.tallerify.presenter.login.LoginNativeDialogPresenter;
import com.u.tallerify.view.login.LoginNativeDialogView;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 4/13/17.
 */
public class LoginNativeDialogController extends AlertDialogController {

    private @Nullable String imageUri;

    @Override
    protected void onAttach(@NonNull final View view) {
        super.onAttach(view);

        CredentialsInteractor.instance().observeToken()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<ReactiveModel<AccessToken>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<AccessToken>>() {
                @Override
                public void call(final ReactiveModel<AccessToken> rxModel) {
                    if (!rxModel.hasError() && rxModel.model() != null) {
                        dismissDialog();
                    }
                }
            });
    }

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
        return BaseDialogPresenter.Severity.CUSTOM;
    }

    @Nullable
    @Override
    protected String imageUrl() {
        return imageUri == null ?
            ImageRequestBuilder.newBuilderWithResourceId(R.drawable.ic_add_black_36dp).build().getSourceUri().toString() :
            imageUri;
    }

    @Override
    protected void observeImageClicks(@NonNull final Observable<Void> observable) {
        observable.observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<Void>bindToLifecycle())
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    // TODO go to add image and swap the last one
                }
            });
    }

}
