package com.u.tallerify.controller.search;

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
import com.u.tallerify.presenter.search.SearchPresenter;
import com.u.tallerify.view.search.SearchView;

/**
 * Created by saguilera on 3/24/17.
 */

public class SearchController extends FlowController {

    private static final String SEARCH_VIEW_TAG = "search_view_tag";

    @NonNull
    @Override
    protected View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup container) {
        return inflater.inflate(R.layout.controller_search, container, false);
    }

    @Override
    protected void onAttach(@NonNull final View view) {
        View searchView = new SearchView(getActivity());
        searchView.setTag(SEARCH_VIEW_TAG);
        getActionBar().addView(searchView);

        super.onAttach(view);

        Coordinators.bind(searchView, new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                return new SearchPresenter();
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
        return null; // TODO removehardcoded
    }

}
