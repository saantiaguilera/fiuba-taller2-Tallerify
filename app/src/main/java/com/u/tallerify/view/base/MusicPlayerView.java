package com.u.tallerify.view.base;

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
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

                private boolean viewPressed;
                private double startY;

                @Override
                public void call(final MotionEvent ev) {
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
                            if (viewPressed) {
                                if (ev.getRawY() - startY < -SCROLL_SWITCH_DELTA &&
                                    mode == MODE.COMPACT) {
                                    transition(MusicPlayerView.this, expandView);
                                    viewPressed = false;
                                    mode = MODE.EXPANDED;
                                }
                            }
                            break;
                    }
                }
            });

        expandView.observeOnOverscrollChanges()
            .debounce(150, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(RxLifecycleAndroid.<OverscrollScrollView.OverScrolledBundle>bindView(expandView))
            .subscribe(new Action1<OverscrollScrollView.OverScrolledBundle>() {
                @Override
                public void call(final OverscrollScrollView.OverScrolledBundle bundle) {
                    if (bundle.clampedY && bundle.scrollY == 0 && mode == MODE.EXPANDED) {
                        transition(MusicPlayerView.this, compactView);
                        mode = MODE.COMPACT;
                    }
                }
            });
    }

    @Override
    public void setCurrentPlay(@NonNull final CurrentPlay currentPlay) {
        compactView.setCurrentPlay(currentPlay);
        expandView.setCurrentPlay(currentPlay);
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

    private enum MODE {
        COMPACT,
        EXPANDED
    }

}