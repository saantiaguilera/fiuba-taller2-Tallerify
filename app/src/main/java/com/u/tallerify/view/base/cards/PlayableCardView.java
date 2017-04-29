package com.u.tallerify.view.base.cards;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.u.tallerify.R;
import com.u.tallerify.contract.base.cards.PlayableCardContract;
import com.u.tallerify.supplier.card.PlayableCardSupplier;
import com.u.tallerify.utils.FrescoImageController;
import com.u.tallerify.utils.MetricsUtils;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 3/12/17.
 */
public class PlayableCardView extends CardView
        implements PlayableCardContract.View {

    private static int bestDimen = 0;

    private @NonNull TextView nameView;
    private @NonNull SimpleDraweeView imageView;

    @NonNull ViewGroup overlayView;
    private @NonNull ImageView playlistView;
    private @NonNull ImageView playView;
    private @NonNull ImageView actionView;

    @Nullable PublishSubject<Void> playlistSubject;
    @Nullable PublishSubject<Void> playSubject;
    @Nullable PublishSubject<Void> actionSubject;

    public PlayableCardView(final Context context) {
        this(context, null);
    }

    public PlayableCardView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayableCardView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_card_playable, this);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
            computeBestDimen(),
            computeBestDimen());
        setLayoutParams(params);

        setRadius(getResources().getDimensionPixelSize(R.dimen.view_card_song_radius));
        setCardElevation(getResources().getDimensionPixelSize(R.dimen.view_card_song_elevation));

        nameView = (TextView) findViewById(R.id.view_card_playable_name);
        imageView = (SimpleDraweeView) findViewById(R.id.view_card_playable_image);

        overlayView = (ViewGroup) findViewById(R.id.view_card_playable_overlay);
        playlistView = (ImageView) findViewById(R.id.view_card_playable_playlist);
        playView = (ImageView) findViewById(R.id.view_card_playable_play);
        actionView = (ImageView) findViewById(R.id.view_card_playable_favorite);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (overlayView.getVisibility() != View.VISIBLE) {
                    showOverlay();

                    Observable.timer(4, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(final Long aLong) {
                                hideOverlay();
                            }
                        });
                }
            }
        });

        playlistView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (playlistSubject != null) {
                    playlistSubject.onNext(null);
                }
                hideOverlay();
            }
        });

        playView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (playSubject != null) {
                    playSubject.onNext(null);
                }
                hideOverlay();
            }
        });

        actionView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (actionSubject != null) {
                    actionSubject.onNext(null);
                }
                hideOverlay();
            }
        });
    }

    @UiThread
    void showOverlay() {
        overlayView.setAlpha(0f);
        overlayView.setVisibility(View.VISIBLE);
        overlayView.animate()
            .setDuration(250)
            .alpha(1)
            .setListener(null)
            .start();
    }

    @UiThread
    void hideOverlay() {
        overlayView.animate()
            .setDuration(250)
            .alpha(0)
            .setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(final Animator animation) {}

                @Override
                public void onAnimationEnd(final Animator animation) {
                    overlayView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(final Animator animation) {}

                @Override
                public void onAnimationRepeat(final Animator animation) {}
            })
            .start();
    }

    @Override
    public void setImage(@Nullable final String url) {
        if (url != null) {
            FrescoImageController.create()
                .load(url)
                .into(imageView);
        } else {
            FrescoImageController.create()
                .load(R.drawable.placeholder_song)
                .into(imageView);
        }
    }

    @Override
    public void setName(@NonNull final String name) {
        nameView.setText(name);
    }

    @Override
    public void setAction(@DrawableRes int resId) {
        actionView.setImageResource(resId);
    }

    @Override
    public void setActionEnabled(final boolean enabled) {
        actionView.setVisibility(enabled ?
            View.VISIBLE :
            View.GONE);
    }

    @NonNull
    @Override
    public Observable<Void> observePlaylistClicks() {
        if (playlistSubject == null) {
            playlistSubject = PublishSubject.create();
        }
        return playlistSubject;
    }

    @NonNull
    @Override
    public Observable<Void> observePlayClicks() {
        if (playSubject == null) {
            playSubject = PublishSubject.create();
        }
        return playSubject;
    }

    @NonNull
    @Override
    public Observable<Void> observeActionClicks() {
        if (actionSubject == null) {
            actionSubject = PublishSubject.create();
        }
        return actionSubject;
    }

    private int computeBestDimen() {
        if (bestDimen != 0) return bestDimen;

        int screenWidth = MetricsUtils.getScreenPixelBounds(getContext()).x;
        int columns = PlayableCardSupplier.SONGS_PER_ROW;

        screenWidth -= (2 * getResources().getDimensionPixelSize(R.dimen.home_item_paddings));
        screenWidth -= ((columns + 1) * getResources().getDimensionPixelSize(R.dimen.home_item_paddings));

        return bestDimen = (screenWidth / columns);
    }

}
