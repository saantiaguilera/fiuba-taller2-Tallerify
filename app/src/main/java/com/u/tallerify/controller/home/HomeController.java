package com.u.tallerify.controller.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.R;
import com.u.tallerify.controller.FlowController;
import com.u.tallerify.presenter.home.HomeCardContainerPresenter;

/**
 * Created by saguilera on 3/12/17.
 */
public class HomeController extends FlowController {

    @NonNull
    @Override
    protected View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup container) {
        return inflater.inflate(R.layout.controller_home, container, false);
    }

    @Override
    protected void onAttach(@NonNull final View view) {
        super.onAttach(view);
        inflateToolbar();
        Coordinators.bind(view, new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                return new HomeCardContainerPresenter();
            }
        });
    }

    private void inflateToolbar() {
        Toolbar toolbar = getActionBar();
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.mipmap.ic_launcher); // TODO settings icon
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    //TODO
                    Toast.makeText(getActivity(), "Settings icon", Toast.LENGTH_SHORT).show();
                }
            });

            toolbar.inflateMenu(R.menu.menu_home);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(final MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_home_search:
                            //TODO
                            Toast.makeText(getActivity(), "Search icon", Toast.LENGTH_SHORT).show();
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }
    }

    @Nullable
    @Override
    protected String title() {
        return getResources().getString(R.string.toolbar_home);
    }

}
