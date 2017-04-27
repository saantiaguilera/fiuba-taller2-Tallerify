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
import com.u.tallerify.presenter.profile.ContactsPresenter;
import com.u.tallerify.presenter.profile.ContactsSearchBarPresenter;
import com.u.tallerify.view.abstracts.BaseInputView;

/**
 * Created by saguilera on 4/26/17.
 */
public class ContactsController extends FlowController {

    private static final @NonNull String SEARCH_VIEW_TAG = ContactsController.class.getName() + "#search_bar_tag";

    @NonNull
    @Override
    protected View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup container) {
        return inflater.inflate(R.layout.controller_contacts, container, false);
    }

    @Override
    protected void onAttach(@NonNull final View view) {
        super.onAttach(view);

        View searchView = new BaseInputView(getActivity());
        searchView.setTag(SEARCH_VIEW_TAG);
        getActionBar().addView(searchView);

        Coordinators.bind(searchView, new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                return new ContactsSearchBarPresenter();
            }
        });
        Coordinators.bind(view, new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                return new ContactsPresenter();
            }
        });
    }

    @Override
    protected void onDetach(@NonNull final View view) {
        getActionBar().removeView(getActionBar().findViewWithTag(SEARCH_VIEW_TAG));
        super.onDetach(view);
    }

    @Nullable
    @Override
    protected String title() {
        return null;
    }

}
