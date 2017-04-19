package com.u.tallerify.controller.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.miguelbcr.ui.rx_paparazzo.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo.entities.Options;
import com.miguelbcr.ui.rx_paparazzo.entities.Response;
import com.miguelbcr.ui.rx_paparazzo.entities.size.SmallSize;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.R;
import com.u.tallerify.contract.login.LoginNativeContract;
import com.u.tallerify.controller.abstracts.AlertDialogController;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.credentials.CredentialsInteractor;
import com.u.tallerify.presenter.abstracts.BaseDialogPresenter;
import com.u.tallerify.presenter.login.LoginNativeDialogPresenter;
import com.u.tallerify.view.login.LoginNativeDialogView;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Created by saguilera on 4/13/17.
 */
public class LoginNativeDialogController extends AlertDialogController {

    @Nullable LoginNativeContract.Presenter presenter;
    @Nullable String imageUri;

    boolean signupVisible;

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
                presenter = new LoginNativeDialogPresenter();

                if (imageUri != null) {
                    onImageChange();
                }

                presenter.observeSignupVisibilityChanges()
                    .observeOn(Schedulers.computation())
                    .subscribeOn(Schedulers.computation())
                    .compose(LoginNativeDialogController.this.<Boolean>bindToLifecycle())
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(final Boolean signupVisible) {
                            LoginNativeDialogController.this.signupVisible = signupVisible;
                            onImageChange();
                        }
                    });

                return (Coordinator) presenter;
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
        return signupVisible ?
                imageUri == null ?
                    ImageRequestBuilder.newBuilderWithResourceId(R.drawable.ic_add_black_36dp).build().getSourceUri().toString() :
                    imageUri :
            ImageRequestBuilder.newBuilderWithResourceId(R.drawable.ic_info_outline_black_36dp).build().getSourceUri().toString();
    }

    @Override
    protected void observeImageClicks(@NonNull final Observable<Void> observable) {
        observable.observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<Void>bindToLifecycle())
            .filter(new Func1<Void, Boolean>() {
                @Override
                public Boolean call(final Void aVoid) {
                    return signupVisible;
                }
            })
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    RxPaparazzo.takeImage(getActivity())
                        .usingGallery()
                        .compose(LoginNativeDialogController.this.<Response<Activity,String>>bindToLifecycle())
                        .observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<Response<Activity, String>>() {
                            @Override
                            public void call(final Response<Activity, String> response) {
                                if (response.resultCode() == RESULT_OK) {
                                    imageUri = response.data();
                                    if (presenter != null) {
                                        presenter.setImage(imageUri);
                                    }
                                    onImageChange();
                                }
                            }
                        });
                }
            });
    }

}
