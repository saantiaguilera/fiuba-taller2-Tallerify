package com.u.tallerify.controller;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import com.u.tallerify.R;
import com.u.tallerify.utils.CurrentPlay;

/**
 * Created by saguilera on 3/12/17.
 */
public abstract class FlowController extends BaseController {

    @Override
    protected void onAttach(@NonNull final View view) {
        super.onAttach(view);

        if (getActionBar() != null) {
            getActionBar().getMenu().clear();

            if (hasActionBar()) {
                getActionBar().setTitle(" " + title());
                getActionBar().setVisibility(View.VISIBLE);
            } else {
                getActionBar().setVisibility(View.GONE);
            }
        }

        renderMediaPlayer(false);
    }

    public void renderMediaPlayer(boolean animated) {
        if (getActivity() != null) {
            View playerView = getActivity().findViewById(R.id.activity_main_player_view);
            if (playerView.getVisibility() != (hasPlayer() ? View.VISIBLE : View.GONE)) {
                playerView.setVisibility(hasPlayer() ? View.VISIBLE : View.GONE);
                if (animated) {
                    playerView.setAlpha(hasPlayer() ? 0f : 1f);
                    playerView.animate()
                        .alpha(hasPlayer() ? 1f : 0f)
                        .start();
                }

                View coordinatorView = getActivity().findViewById(R.id.activity_main_coordinator_layout);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) coordinatorView.getLayoutParams();
                params.bottomMargin = hasPlayer() ?
                    getActivity().getResources().getDimensionPixelSize(R.dimen.view_music_player_height) : 0;
                coordinatorView.setLayoutParams(params);
            }
        }
    }

    protected boolean hasActionBar() {
        return true;
    }

    protected boolean hasPlayer() {
        return CurrentPlay.instance() != null;
    }

    protected @Nullable Toolbar getActionBar() {
        return getActivity() == null ? null : (Toolbar) getActivity().findViewById(R.id.activity_main_toolbar);
    }

    protected abstract @Nullable String title();

}
