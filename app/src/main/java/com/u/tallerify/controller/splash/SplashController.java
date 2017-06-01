package com.u.tallerify.controller.splash;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.rxlifecycle.ControllerEvent;
import com.facebook.drawee.view.DraweeView;
import com.u.tallerify.R;
import com.u.tallerify.controller.FlowController;
import com.u.tallerify.controller.home.HomeController;
import com.u.tallerify.networking.AccessTokenManager;
import com.u.tallerify.networking.interactor.credentials.CredentialsInteractor;
import com.u.tallerify.utils.BussinessUtils;
import com.u.tallerify.utils.FrescoImageController;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/12/17.
 */

public class SplashController extends FlowController {

    /**
     * Default minimum time for the activity to be shown
     */
    private static final long SPLASH_DEFAULT_TIME = 6L;

    @NonNull
    @Override
    protected View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup container) {
        setRetainViewMode(RetainViewMode.RELEASE_DETACH);
        return inflater.inflate(R.layout.controller_splash, container, false);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onAttach(@NonNull final View view) {
        super.onAttach(view);

        FrescoImageController.create()
            .load(R.drawable.loading_view)
            .autoPlayAnimations(true)
            .into((DraweeView) view.findViewById(R.id.controller_splash_gif));

        if (AccessTokenManager.instance().read(getActivity()) != null) {
            BussinessUtils.requestBasicInfo(getActivity());
            BussinessUtils.requestRecommendations(getActivity());
            CredentialsInteractor.instance().dispatchToken(AccessTokenManager.instance().snapshot());
        }

        BussinessUtils.requestTrendings(getActivity());

        Observable.timer(SPLASH_DEFAULT_TIME, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(this.<Long>bindUntilEvent(ControllerEvent.DESTROY_VIEW))
            .subscribe(new Action1<Long>() {
                @Override
                public void call(final Long aLong) {
                    getRouter()
                        .setRoot(RouterTransaction.with(new HomeController())
                            .popChangeHandler(new FadeChangeHandler(false))
                            .pushChangeHandler(new FadeChangeHandler()));
                }
            });
    }

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Override
    protected boolean hasPlayer() {
        return false;
    }

    @Nullable
    @Override
    protected String title() {
        return null;
    }

}
