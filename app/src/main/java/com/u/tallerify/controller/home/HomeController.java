package com.u.tallerify.controller.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.R;
import com.u.tallerify.controller.FlowController;
import com.u.tallerify.controller.profile.ProfileController;
import com.u.tallerify.controller.search.SearchController;
import com.u.tallerify.networking.AccessTokenManager;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.credentials.CredentialsInteractor;
import com.u.tallerify.presenter.home.HomePresenter;
import com.u.tallerify.utils.BussinessUtils;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/12/17.
 */
public class HomeController extends FlowController {

    private @Nullable CoordinatorProvider provider;

    boolean isUserLogged;

    @NonNull
    @Override
    protected View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup container) {
        setRetainViewMode(RetainViewMode.RETAIN_DETACH); // but probably this is the desired ux behavior
        return inflater.inflate(R.layout.controller_home, container, false);
    }

    @Override
    protected void onAttach(@NonNull final View view) {
        super.onAttach(view);

        if (AccessTokenManager.instance().snapshot() != null) {
            BussinessUtils.requestRecommendations(getApplicationContext());
        }

        CredentialsInteractor.instance().observeToken()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<ReactiveModel<String>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<String>>() {
                @Override
                public void call(final ReactiveModel<String> accessTokenReactiveModel) {
                    if (!accessTokenReactiveModel.hasError() && accessTokenReactiveModel.model() != null) {
                        BussinessUtils.requestBasicInfo(getApplicationContext());

                        isUserLogged = true;
                    } else {
                        isUserLogged = false;
                    }

                    getActivity().invalidateOptionsMenu();
                }
            });

        if (provider == null) {
            // Keep it because since its retained in detach, it will queue presenters a same view if not
            provider = new CoordinatorProvider() {
                @Nullable
                @Override
                public Coordinator provideCoordinator(final View view) {
                    return new HomePresenter();
                }
            };

            Coordinators.bind(view, provider);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull final Menu menu, @NonNull final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        menu.findItem(R.id.menu_home_profile).setVisible(isUserLogged);
        menu.findItem(R.id.menu_home_search).setVisible(isUserLogged);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        Controller controller = null;
        switch (item.getItemId()) {
            case R.id.menu_home_search:
                controller = new SearchController();
                break;
            case R.id.menu_home_profile:
                controller = new ProfileController();
        }

        if (controller == null) {
            return false;
        }

        getRouter().pushController(RouterTransaction.with(controller)
            .pushChangeHandler(new FadeChangeHandler())
            .popChangeHandler(new FadeChangeHandler(false)));

        return true;
    }

    @Nullable
    @Override
    protected String title() {
        return getResources().getString(R.string.toolbar_home);
    }

    @Override
    protected boolean hasOptionsMenu() {
        return true;
    }

}
