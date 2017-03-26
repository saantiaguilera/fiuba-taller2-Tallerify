package com.u.tallerify.view.base.music_player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.transition.ChangeBounds;
import android.support.transition.Scene;
import android.support.transition.TransitionManager;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.u.tallerify.R;
import com.u.tallerify.contract.base.MusicPlayerContract;
import com.u.tallerify.utils.CurrentPlay;
import com.u.tallerify.utils.MetricsUtils;
import com.u.tallerify.view.base.music_player.internal.MusicPlayerCompactView;
import com.u.tallerify.view.base.music_player.internal.MusicPlayerExpandedView;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by saguilera on 3/13/17.
 */
public class MusicPlayerView extends FrameLayout
        implements MusicPlayerContract.View {

    static int SCROLL_SWITCH_DELTA;

    @NonNull MODE mode;

    @NonNull MusicPlayerCompactView compactView;
    @NonNull MusicPlayerExpandedView expandView;

    public MusicPlayerView(final Context context) {
        this(context, null);
    }

    public MusicPlayerView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicPlayerView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        SCROLL_SWITCH_DELTA = MetricsUtils.getScreenPixelBounds(context).y / 5;

        compactView = new MusicPlayerCompactView(context);
        expandView = new MusicPlayerExpandedView(context);

        mode = MODE.COMPACT;
        addView(compactView);
        setupTransitionLogic();

        setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
    }

    void transition(@NonNull ViewGroup triggeringView, @NonNull ViewGroup targetView) {
        ChangeBounds transition = new ChangeBounds();
        Scene scene = new Scene(triggeringView, targetView);
        scene.setExitAction(new Runnable() {
            @Override
            public void run() {
                setupTransitionLogic();
            }
        });

        TransitionManager.go(scene, transition);
    }

    void setupTransitionLogic() {
        RxView.touches(compactView)
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleAndroid.<MotionEvent>bindView(compactView))
            .subscribe(new Action1<MotionEvent>() {

                private long lastTouchDown;
                private boolean viewPressed;
                private double startY;

                @Override
                public void call(final MotionEvent ev) {
                    switch (ev.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            viewPressed = true;
                            startY = ev.getRawY();
                            lastTouchDown = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_UP:
                            viewPressed = false;
                            startY = 0;
                            if (mode == MODE.COMPACT && System.currentTimeMillis() - lastTouchDown < 250) {
                                mode = MODE.EXPANDED;
                                transition(MusicPlayerView.this, expandView);
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (viewPressed) {
                                if (ev.getRawY() - startY < -SCROLL_SWITCH_DELTA &&
                                    mode == MODE.COMPACT) {
                                    mode = MODE.EXPANDED;
                                    transition(MusicPlayerView.this, expandView);
                                    viewPressed = false;
                                }
                            }
                            break;
                    }
                }
            });

        RxView.touches(expandView)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleAndroid.<MotionEvent>bindView(expandView))
            .subscribe(new Action1<MotionEvent>() {

                private boolean viewPressed;
                private double startY;

                @Override
                public void call(final MotionEvent ev) {
                    expandView.onTouchEvent(ev);
                    switch (ev.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            viewPressed = true;
                            startY = ev.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            viewPressed = false;
                            startY = 0;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (expandView.getScrollY() != 0) {
                                startY = ev.getRawY();
                                break;
                            }
                            if (viewPressed && ev.getRawY() - startY > SCROLL_SWITCH_DELTA &&
                                    mode == MODE.EXPANDED) {
                                transition(MusicPlayerView.this, compactView);
                                mode = MODE.COMPACT;
                                viewPressed = false;
                            }
                            break;
                    }

                }
            });
    }

    @Override
    public void setQueue(@NonNull List<String> names, @NonNull List<String> urls) {
        expandView.setQueue(names, urls);
    }

    @Override
    public void setImage(@NonNull final String picture) {
        compactView.setImageUrl(picture);
        expandView.setImageUrl(picture);
    }

    @Override
    public void setPlaying() {
        compactView.setPlaying();
        expandView.setPlaying();
    }

    @Override
    public void setPaused() {
        compactView.setPaused();
        expandView.setPaused();
    }

    @Override
    public void setName(@NonNull final String songName, @NonNull final String artistName) {
        compactView.setTitle(songName + " - " + artistName);
        expandView.setSongName(songName);
        expandView.setArtistName(artistName);
    }

    @Override
    public void setTrackBarMax(final int max) {
        expandView.setTrackBarMax(max);
    }

    @Override
    public void setTrackBarProgress(final int progress) {
        expandView.setTrackBarProgress(progress);
    }

    @Override
    public void setTime(final int time, final int duration) {
        expandView.setTime(time, duration);
    }

    @Override
    public void setVolume(final int volume) {
        expandView.setVolume(volume);
    }

    @Override
    public void setShuffleEnabled(final boolean enabled) {
        expandView.setShuffleEnabled(enabled);
    }

    @Override
    public void setRepeatMode(final CurrentPlay.RepeatMode repeatMode) {
        expandView.setRepeatMode(repeatMode);
    }

    @Override
    public void setRating(final int rating, final boolean enabled) {
        expandView.setRating(rating, enabled);
    }

    @Override
    public void setFavorite(final boolean favved, final boolean enabled) {
        expandView.setFavorite(favved, enabled);
    }

    @NonNull
    @Override
    public Observable<Void> observeNextSongClicks() {
        return Observable.merge(compactView.observeNextSongClicks(),
                expandView.observeNextSongClicks());
    }

    @NonNull
    @Override
    public Observable<Void> observePlayStateClicks() {
        return Observable.merge(compactView.observePlayStateClicks(),
                expandView.observePlayStateClicks());
    }

    @NonNull
    @Override
    public Observable<Void> observePreviousSongClicks() {
        return expandView.observePreviousSongClicks();
    }

    @NonNull
    @Override
    public Observable<Integer> observeVolumeSeeks() {
        return expandView.observeVolumeSeeks();
    }

    @NonNull
    @Override
    public Observable<Integer> observeSongSeeks() {
        return expandView.observeSongSeeks();
    }

    @NonNull
    @Override
    public Observable<Void> observeShuffleClicks() {
        return expandView.observeShuffleClicks();
    }

    @NonNull
    @Override
    public Observable<Void> observeRepeatClicks() {
        return expandView.observeRepeatClicks();
    }

    @NonNull
    @Override
    public Observable<Integer> observeRatingSeeks() {
        return expandView.observeRatingSeeks();
    }

    @NonNull
    @Override
    public Observable<Boolean> observeFavoriteClicks() {
        return expandView.observeFavoriteClicks();
    }

    @NonNull
    @Override
    public Observable<Integer> observePlaylistSkipClicks() {
        return expandView.observePlaylistSkipClicks();
    }

    private enum MODE {
        COMPACT,
        EXPANDED
    }

}