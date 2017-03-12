package com.u.tallerify.controller.splash;

import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.rxlifecycle.ControllerEvent;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.u.tallerify.R;
import com.u.tallerify.controller.FlowController;
import com.u.tallerify.controller.home.HomeController;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.AccessTokenManager;
import com.u.tallerify.networking.RestClient;
import com.u.tallerify.networking.services.user.UserService;
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

        if (AccessTokenManager.getInstance().read(getActivity()) != null) {
            // If we have an access token, get the user
            UserService service = RestClient.with(getActivity()).create(UserService.class);
            service.currentUser().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<User>bindUntilEvent(ControllerEvent.DESTROY_VIEW))
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        // TODO handle error
                        SplashController.this.onFinish(throwable, null);
                    }
                })
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(final User user) {
                        SplashController.this.onFinish(null, user);
                    }
                }).subscribe();
        }

        Observable.timer(SPLASH_DEFAULT_TIME, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(this.<Long>bindUntilEvent(ControllerEvent.DESTROY_VIEW))
            .subscribe(new Action1<Long>() {
                @Override
                public void call(final Long aLong) {
                    SplashController.this.onFinish(null, null);
                }
            });
    }

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Nullable
    @Override
    protected String title() {
        return null;
    }

    /**
     * Logic for finishing the splash.
     * This should only be called either when all requirements are fullfilled and the minimum time has passed.
     */
    @SuppressWarnings("ConstantConditions")
    void onFinish(@Nullable Throwable ex, @Nullable User user) {
        if (AccessTokenManager.getInstance().read(getActivity()) != null) {
            // A request for the user was available
            if (user != null) {
                // The request already finished, go to home with credentials
                // startActivity(new Intent()); // TODO
            }

            if (ex != null) {
                // The request had errors, go to home directly too
            }

            // If not wait the request to finish
        } else {
            // If no request was able to perform, go to home directly
            // startActivity(new Intent()); // TODO
        }

        setRetainViewMode(RetainViewMode.RELEASE_DETACH);
        getRouter().pushController(RouterTransaction.with(new HomeController()));
    }

}
