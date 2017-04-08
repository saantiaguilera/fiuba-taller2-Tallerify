package com.u.tallerify.controller.playlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.controller.abstracts.AlertDialogController;
import com.u.tallerify.presenter.abstracts.BaseDialogPresenter;
import com.u.tallerify.presenter.playlist.AddToPlaylistPresenter;
import com.u.tallerify.view.playlist.AddToPlaylistView;

/**
 * Created by saguilera on 4/8/17.
 */

public class AddToPlaylistController extends AlertDialogController {

    @NonNull
    @Override
    protected View content() {
        View content = new AddToPlaylistView(getActivity());

        Coordinators.bind(content, new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                return new AddToPlaylistPresenter();
            }
        });

        return content;
    }

    @NonNull
    @Override
    protected String title() {
        return "En que playlist deseas agregarlo?";
    }

    @NonNull
    @Override
    protected BaseDialogPresenter.Severity severity() {
        return BaseDialogPresenter.Severity.INFO;
    }

}
