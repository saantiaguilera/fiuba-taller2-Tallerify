package com.u.tallerify.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import com.u.tallerify.model.ResolvedUri;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.song.SongInteractor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 4/7/17.
 */
public final class PlayManager {

    private static final PlayManager INSTANCE = new PlayManager();

    @NonNull MediaPlayer mediaPlayer;
    @Nullable Subscription subscription;
    boolean preparing;

    private PlayManager() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public static @NonNull PlayManager instance() {
        return INSTANCE;
    }

    public void resume() {
        play();
    }

    public void pause() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        mediaPlayer.pause();
    }

    void play() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        int count = (int) (CurrentPlay.instance().duration() - CurrentPlay.instance().time());
        subscription = Observable.interval(1, TimeUnit.SECONDS)
            .take(count > 0 ? count : 1)
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .subscribe(new Action1<Long>() {
                @Override
                public void call(final Long aLong) {
                    CurrentPlay.instance().newBuilder()
                        .time(mediaPlayer.getCurrentPosition() / 1000)
                        .build();
                }
            });

        Observable.just(null)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.computation())
            .subscribe(new Action1<Object>() {
                @Override
                public void call(final Object o) {
                    mediaPlayer.start();
                }
            });
    }

    public void start(@NonNull Context context) {
        if (!preparing) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            SongInteractor.instance().resolve(context, CurrentPlay.instance().song())
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ResolvedUri>() {
                    @Override
                    public void call(final ResolvedUri uri) {
                        try {
                            preparing = true;
                            mediaPlayer.setDataSource(uri.url());
                            mediaPlayer.prepare();
                            preparing = false;

                            CurrentPlay.instance().newBuilder()
                                .duration(mediaPlayer.getDuration() / 1000)
                                .build();

                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(final MediaPlayer mp) {
                                    PlayUtils.forward();
                                }
                            });

                            if (CurrentPlay.instance().playState() == CurrentPlay.PlayState.PLAYING) {
                                play();
                            }
                        } catch (Exception e) {
                            preparing = false;
                        }
                    }
                }, Interactors.ACTION_ERROR);
        }
    }

    public void release() {
        mediaPlayer.release();
    }

    /**
     * Seek to a position
     * @param newTime in seconds
     */
    public void seek(long newTime) {
        // To avoid auto lagging ourselves we use a threshold of 2 seconds at least for seeks
        if (newTime - (mediaPlayer.getCurrentPosition() / 1000) > 2) {
            mediaPlayer.seekTo((int) (newTime * 1000));
        }
    }

}
