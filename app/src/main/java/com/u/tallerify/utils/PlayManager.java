package com.u.tallerify.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import com.u.tallerify.model.ResolvedUri;
import com.u.tallerify.networking.interactor.song.SongInteractor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
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

        subscription = Observable.timer(mediaPlayer.getDuration() - CurrentPlay.instance().currentTime(), TimeUnit.SECONDS)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    mediaPlayer.start();
                }
            })
            .subscribe(new Action1<Long>() {
                @Override
                public void call(final Long aLong) {
                    CurrentPlay.instance().newBuilder()
                        .currentTime(mediaPlayer.getCurrentPosition() / 1000)
                        .build();
                }
            });
    }

    public void start(@NonNull Context context) {
        if (!preparing) {
            SongInteractor.instance().resolve(context, CurrentPlay.instance().currentSong())
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
                });
        }
    }

    /**
     * Seek to a position
     * @param newTime in seconds
     */
    public void seek(long newTime) {
        mediaPlayer.seekTo((int) (newTime * 1000));
    }

}
