package com.u.tallerify.view.base;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.u.tallerify.R;
import com.u.tallerify.utils.CurrentPlay;
import com.u.tallerify.utils.FrescoImageController;
import com.u.tallerify.view.RxView;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 3/14/17.
 */
public class MusicPlayerExpandedView extends OverscrollScrollView {

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

    private @Nullable PublishSubject<Void> nextTrackSubject;
    private @Nullable PublishSubject<Void> previousTrackSubject;
    private @Nullable PublishSubject<Void> playStatusTrackSubject;
    private @Nullable PublishSubject<Integer> volumeSubject;
    private @Nullable PublishSubject<Integer> trackBarSubject;

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

        expandTrackBar.getProgressDrawable().setColorFilter(
            new PorterDuffColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null),
                PorterDuff.Mode.SRC_IN));

        expandVolumeBar.getProgressDrawable().setColorFilter(
            new PorterDuffColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null),
                PorterDuff.Mode.SRC_IN));

        expandTrackBar.getThumb().setColorFilter(
            new PorterDuffColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null),
                PorterDuff.Mode.SRC_IN));

        expandVolumeBar.getThumb().setColorFilter(
            new PorterDuffColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null),
                PorterDuff.Mode.SRC_IN));
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

    public void setCurrentPlay(final @NonNull CurrentPlay currentPlay) {
        FrescoImageController.create()
            .load(currentPlay.currentSong().album().picture().large())
            .into(expandImage);

        if (currentPlay.playState() == CurrentPlay.PlayState.PLAYING) {
            expandPlayPause.setImageResource(R.drawable.ic_play_arrow_black_36dp);
        } else {
            expandPlayPause.setImageResource(R.drawable.ic_pause_black_36dp);
        }

        expandTrackArtist.setText(currentPlay.currentSong().album().artist().name());
        expandTrackName.setText(currentPlay.currentSong().name());

        expandTrackBar.setProgress((int) (currentPlay.currentTime() * 100.0 / currentPlay.currentSong().duration()));
        expandTrackTime.setText(currentPlay.currentTime() / 60 + ":" + currentPlay.currentTime() % 60);
        expandTrackTimeLeft.setText((currentPlay.currentSong().duration() - currentPlay.currentTime()) / 60 + ":" +
            (currentPlay.currentSong().duration() - currentPlay.currentTime()) % 60);

        expandVolumeBar.setProgress(currentPlay.volume());
    }

}
