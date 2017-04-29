package com.u.tallerify.controller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import com.u.tallerify.R;
import com.u.tallerify.utils.CurrentPlay;
import com.u.tallerify.view.base.music_player.MusicPlayerView;

/**
 * Created by saguilera on 3/12/17.
 */
public abstract class FlowController extends BaseController {

    @Override
    protected void onAttach(@NonNull final View view) {
        super.onAttach(view);
        setHasOptionsMenu(hasOptionsMenu());

        if (hasActionBar()) {
            if (title() != null) {
                getActionBar().setTitle(" " + title());
            } else {
                getActionBar().setTitle("");
            }

            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) getActionBar().getLayoutParams();
            if (hasScrollingActionBar()) {
                params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL|AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            } else {
                params.setScrollFlags(0);
            }

            if (!hasOptionsMenu()) {
                getActionBar().getMenu().clear();
            }

            getActionBar().setVisibility(View.VISIBLE);
        } else {
            getActionBar().setVisibility(View.GONE);
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

    @Override
    public boolean handleBack() {
        if (hasPlayer()) {
            MusicPlayerView view = (MusicPlayerView) getActivity().findViewById(R.id.activity_main_player_view);
            if (view.currentMode() == MusicPlayerView.MODE.EXPANDED) {
                view.setMode(MusicPlayerView.MODE.COMPACT);
                return true;
            }
        }

        return super.handleBack();
    }

    protected boolean hasScrollingActionBar() {
        return true;
    }

    protected boolean hasActionBar() {
        return true;
    }

    protected boolean hasOptionsMenu() {
        return false;
    }

    protected boolean hasPlayer() {
        return CurrentPlay.instance() != null;
    }

    protected @Nullable Toolbar getActionBar() {
        return getActivity() == null ? null : (Toolbar) getActivity().findViewById(R.id.activity_main_toolbar);
    }

    protected abstract @Nullable String title();

}
