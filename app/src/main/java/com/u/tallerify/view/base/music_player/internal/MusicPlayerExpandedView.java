package com.u.tallerify.view.base.music_player.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.u.tallerify.R;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.utils.CurrentPlay;
import com.u.tallerify.utils.FrescoImageController;
import com.u.tallerify.view.RxView;
import java.util.List;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Ideally instead of having this mega class, we should have components like:
 * - VolumeView
 * - PlayControlsView
 * - etc.
 *
 * No time.
 *
 * Created by saguilera on 3/14/17.
 */
public class MusicPlayerExpandedView extends ScrollView {

    private @NonNull SimpleDraweeView expandImage;
    private @NonNull SeekBar expandTrackBar;
    private @NonNull TextView expandTrackTime;
    private @NonNull TextView expandTrackTimeLeft;
    private @NonNull TextView expandTrackName;
    private @NonNull TextView expandTrackArtist;
    private @NonNull ImageView expandPreviousTrack;
    private @NonNull ImageView expandNextTrack;
    private @NonNull ImageView expandPlayPause;
    private @NonNull SeekBar expandVolumeBar;
    private @NonNull ImageView expandRepeat;
    private @NonNull ImageView expandShuffle;
    private @NonNull ImageView expandFavorite;
    private @NonNull RatingBar expandRatingBar;
    private @NonNull LinearLayout expandPlaylistContainer;

    private @Nullable PublishSubject<Void> nextTrackSubject;
    private @Nullable PublishSubject<Void> previousTrackSubject;
    private @Nullable PublishSubject<Void> playStatusTrackSubject;
    private @Nullable PublishSubject<Void> repeatSubject;
    private @Nullable PublishSubject<Void> shuffleSubject;
    private @Nullable PublishSubject<Integer> volumeSubject;
    private @Nullable PublishSubject<Integer> trackBarSubject;
    private @Nullable PublishSubject<Integer> rateBarSubject;
    private @Nullable PublishSubject<Void> favoriteSubject;
    @Nullable PublishSubject<Integer> skipSongsSubject;

    public MusicPlayerExpandedView(final Context context) {
        this(context, null);
    }

    public MusicPlayerExpandedView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicPlayerExpandedView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_music_player_expanded, this);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        setFillViewport(true);

        expandImage = (SimpleDraweeView) findViewById(R.id.view_music_player_expanded_image);
        expandNextTrack = (ImageView) findViewById(R.id.view_music_player_expanded_next_track);
        expandPlayPause = (ImageView) findViewById(R.id.view_music_player_expanded_play_pause);
        expandPreviousTrack = (ImageView) findViewById(R.id.view_music_player_expanded_previous_track);
        expandTrackArtist = (TextView) findViewById(R.id.view_music_player_expanded_track_artist);
        expandTrackName = (TextView) findViewById(R.id.view_music_player_expanded_track_name);
        expandTrackBar = (SeekBar) findViewById(R.id.view_music_player_expanded_track_progress);
        expandTrackTime = (TextView) findViewById(R.id.view_music_player_expanded_track_time);
        expandTrackTimeLeft = (TextView) findViewById(R.id.view_music_player_expanded_track_time_left);
        expandVolumeBar = (SeekBar) findViewById(R.id.view_music_player_expanded_volume);
        expandShuffle = (ImageView) findViewById(R.id.view_music_player_expanded_shuffle);
        expandRepeat = (ImageView) findViewById(R.id.view_music_player_expanded_repeat);
        expandFavorite = (ImageView) findViewById(R.id.view_music_player_expanded_favorite);
        expandRatingBar = (RatingBar) findViewById(R.id.view_music_player_expanded_rating_bar);
        expandPlaylistContainer = (LinearLayout) findViewById(R.id.view_music_player_expanded_playlist);

        tintDrawable(expandTrackBar.getProgressDrawable());
        tintDrawable(expandVolumeBar.getProgressDrawable());
        tintDrawable(expandTrackBar.getThumb());
        tintDrawable(expandVolumeBar.getThumb());

        expandTrackName.setSelected(true);
    }

    public @NonNull Observable<Void> observePlayStateClicks() {
        if (playStatusTrackSubject == null) {
            playStatusTrackSubject = PublishSubject.create();
            RxView.dispatchClicks(expandPlayPause, playStatusTrackSubject);
        }

        return playStatusTrackSubject;
    }

    public @NonNull Observable<Void> observeNextSongClicks() {
        if (nextTrackSubject == null) {
            nextTrackSubject = PublishSubject.create();
            RxView.dispatchClicks(expandNextTrack, nextTrackSubject);
        }

        return nextTrackSubject;
    }

    public @NonNull Observable<Integer> observePlaylistSkipClicks() {
        if (skipSongsSubject == null) {
            skipSongsSubject = PublishSubject.create();
        }

        return skipSongsSubject;
    }

    public @NonNull Observable<Void> observeFavoriteClicks() {
        if (favoriteSubject == null) {
            favoriteSubject = PublishSubject.create();
            RxView.dispatchClicks(expandFavorite, favoriteSubject);
        }

        return favoriteSubject;
    }

    public @NonNull Observable<Integer> observeRatingSeeks() {
        if (rateBarSubject == null) {
            rateBarSubject = PublishSubject.create();
            RxView.dispatchSeeks(expandRatingBar, rateBarSubject);
        }

        return rateBarSubject;
    }

    public @NonNull Observable<Void> observePreviousSongClicks() {
        if (previousTrackSubject == null) {
            previousTrackSubject = PublishSubject.create();
            RxView.dispatchClicks(expandPreviousTrack, previousTrackSubject);
        }

        return previousTrackSubject;
    }

    public @NonNull Observable<Integer> observeVolumeSeeks() {
        if (volumeSubject == null) {
            volumeSubject = PublishSubject.create();
            RxView.dispatchSeeks(expandVolumeBar, volumeSubject);
        }

        return volumeSubject;
    }

    public @NonNull Observable<Integer> observeSongSeeks() {
        if (trackBarSubject == null) {
            trackBarSubject = PublishSubject.create();
            RxView.dispatchSeeks(expandTrackBar, trackBarSubject);
        }

        return trackBarSubject;
    }

    public @NonNull Observable<Void> observeShuffleClicks() {
        if (shuffleSubject == null) {
            shuffleSubject = PublishSubject.create();
            RxView.dispatchClicks(expandShuffle, shuffleSubject);
        }

        return shuffleSubject;
    }

    public @NonNull Observable<Void> observeRepeatClicks() {
        if (repeatSubject == null) {
            repeatSubject = PublishSubject.create();
            RxView.dispatchClicks(expandRepeat, repeatSubject);
        }

        return repeatSubject;
    }

    private void attachLast(@NonNull String name, @NonNull String url) {
        final MusicPlayerNextSong view = new MusicPlayerNextSong(getContext());
        view.setTitle(name);
        view.setImageUrl(url);
        view.setTag(expandPlaylistContainer.getChildCount() + 1);

        expandPlaylistContainer.addView(view);

        com.jakewharton.rxbinding.view.RxView.clicks(view)
            .compose(RxLifecycleAndroid.<Void>bindView(view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    if (skipSongsSubject != null) {
                        skipSongsSubject.onNext((Integer) view.getTag());
                    }
                }
            });
    }

    public void setQueue(@NonNull List<String> names, @NonNull List<String> urls) {
        if (names.size() != urls.size()) throw new IllegalStateException("Names size != urls size");

        if (expandPlaylistContainer.getChildCount() > names.size()) {
            for (int i = expandPlaylistContainer.getChildCount() ; i > names.size() ; --i) {
                int absPosition = i - 1;
                expandPlaylistContainer.removeViewAt(absPosition);
            }
        }

        for (int i = 0 ; i < names.size() ; ++i) {
            if (expandPlaylistContainer.getChildCount() < i + 1) {
                attachLast(names.get(i), urls.get(i));
            } else {
                MusicPlayerNextSong currentSong = (MusicPlayerNextSong) expandPlaylistContainer.getChildAt(i);

                currentSong.setImageUrl(urls.get(i));
                currentSong.setTitle(names.get(i));
            }
        }
    }

    public void setImageUrl(@NonNull String url) {
        FrescoImageController.create()
            .load(url)
            .into(expandImage);
    }

    public void setPlaying() {
        expandPlayPause.setImageResource(R.drawable.ic_play_arrow_black_36dp);
    }

    public void setPaused() {
        expandPlayPause.setImageResource(R.drawable.ic_pause_black_36dp);
    }

    public void setArtistName(@NonNull String name) {
        expandTrackArtist.setText(name);
    }

    public void setSongName(@NonNull String name) {
        expandTrackName.setText(name);
    }

    public void setTrackBarMax(int max) {
        expandTrackBar.setMax(max);
    }

    public void setTrackBarProgress(int progress) {
        expandTrackBar.setProgress(progress);
    }

    @SuppressLint("DefaultLocale")
    public void setTime(int time, int duration) {
        expandTrackTime.setText(String.format("%02d", time / 60) +
            ":" +
            String.format("%02d", time % 60));
        expandTrackTimeLeft.setText(
            String.format("%02d", (duration - time) / 60) +
                ":" +
                String.format("%02d", (duration - time) % 60));
    }

    public void setVolume(int volume) {
        expandVolumeBar.setProgress(volume);
    }

    public void setShuffleEnabled(boolean enabled) {
        expandShuffle.getDrawable().setColorFilter(enabled ?
            new PorterDuffColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null),
                PorterDuff.Mode.SRC_IN) :
            null);
    }

    public void setRepeatMode(CurrentPlay.RepeatMode repeatMode) {
        switch (repeatMode) {
            case NONE:
                expandRepeat.setImageResource(R.drawable.ic_repeat_black_36dp);
                expandRepeat.getDrawable().setColorFilter(null);
                break;
            case SINGLE:
                expandRepeat.setImageResource(R.drawable.ic_repeat_one_black_36dp);
                tintDrawable(expandRepeat.getDrawable());
                break;
            case ALL:
                expandRepeat.setImageResource(R.drawable.ic_repeat_black_36dp);
                tintDrawable(expandRepeat.getDrawable());
        }
    }

    public void setRating(int rating, boolean enabled) {
        if (enabled) {
            expandRatingBar.setVisibility(View.VISIBLE);
            expandRatingBar.setRating(rating);
        } else {
            expandRatingBar.setVisibility(View.GONE);
        }
    }

    public void setFavorite(boolean favved, boolean enabled) {
        if (enabled) {
            expandFavorite.setVisibility(View.VISIBLE);
            expandFavorite.getDrawable().setColorFilter(favved ?
                new PorterDuffColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null),
                    PorterDuff.Mode.SRC_IN) :
                new PorterDuffColorFilter(ResourcesCompat.getColor(getResources(), R.color.white, null),
                    PorterDuff.Mode.SRC_IN) );
        } else {
            expandFavorite.setVisibility(View.GONE);
        }
    }

    private void tintDrawable(@NonNull Drawable drawable) {
        drawable.setColorFilter(new PorterDuffColorFilter(
            ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null),
            PorterDuff.Mode.SRC_IN));
    }

}