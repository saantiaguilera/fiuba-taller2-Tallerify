package com.u.tallerify.controller.profile;

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
import com.u.tallerify.R;
import com.u.tallerify.controller.FlowController;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.presenter.AbstractPresenterGraph;
import com.u.tallerify.presenter.Presenter;
import com.u.tallerify.presenter.profile.ProfileUserContactsPresenter;
import com.u.tallerify.presenter.profile.ProfileUserInfoPresenter;
import com.u.tallerify.utils.CoordinatorsInstaller;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/30/17.
 */
public class ProfileController extends FlowController {

    private static final int KEY_CONTACTS = 1;
    private static final int KEY_INFO = 2;

    @NonNull
    @Override
    protected View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup container) {
        View view = inflater.inflate(R.layout.controller_profile, container, false);

        view.findViewById(R.id.controller_profile_info).setTag(KEY_INFO);
        view.findViewById(R.id.controller_profile_contacts).setTag(KEY_CONTACTS);

        return view;
    }

    @Override
    protected void onAttach(@NonNull final View view) {
        super.onAttach(view);

        MeInteractor.instance().currentUser(getActivity())
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<User>bindToLifecycle())
            .subscribe(Interactors.ACTION_NEXT, Interactors.ACTION_ERROR);

        CoordinatorsInstaller.installBinder(
            (ViewGroup) view.findViewById(R.id.controller_profile_root),
            new CoordinatorProvider() {
                private Graph graph;

                @Nullable
                @Override
                public Coordinator provideCoordinator(final View view) {
                    if (graph == null) {
                        graph = new Graph();
                    }
                    return graph.present(view);
                }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull final Menu menu, @NonNull final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        Controller controller = null;
        switch (item.getItemId()) {
            case R.id.menu_profile_add_contacts:
                controller = new ContactsController();
                break;
        }

        if (controller == null) {
            return false;
        }

        getRouter().pushController(RouterTransaction.with(controller)
            .pushChangeHandler(new FadeChangeHandler())
            .popChangeHandler(new FadeChangeHandler(false)));

        return true;
    }

    @Override
    protected boolean hasOptionsMenu() {
        return true;
    }

    @Nullable
    @Override
    protected String title() {
        return getResources().getString(R.string.toolbar_profile);
    }

    private static class Graph extends AbstractPresenterGraph {

        Graph() {
            add(KEY_INFO, new ProfileUserInfoPresenter());
            add(KEY_CONTACTS, new ProfileUserContactsPresenter());
        }

        @Nullable
        @Override
        public Presenter<?> present(@NonNull final View view) {
            return get((Integer) view.getTag());
        }

        @Override
        public int size() {
            return 2;
        }

    }

}
