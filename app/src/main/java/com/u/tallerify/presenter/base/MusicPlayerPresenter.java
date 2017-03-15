package com.u.tallerify.presenter.base;

import android.content.Context;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.view.View;
import com.u.tallerify.contract.base.MusicPlayerContract;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.presenter.Presenter;
import com.u.tallerify.utils.CurrentPlay;
import java.util.List;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/15/17.
 */
@SuppressWarnings("ConstantConditions")
public class MusicPlayerPresenter extends Presenter<MusicPlayerContract.View>
        implements MusicPlayerContract.Presenter {

    @Override
    protected void onAttach(@NonNull final MusicPlayerContract.View view) {
        if (CurrentPlay.instance() != null) {
            view.setCurrentPlay(CurrentPlay.instance());
        }

        CurrentPlay.observeCurrentPlay()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<CurrentPlay>bindToLifecycle((View) view))
            .subscribe(new Action1<CurrentPlay>() {
                @Override
                public void call(final CurrentPlay currentPlay) {
                    requestView();
                }
            });

        view.observeSongSeeks()
            .observeOn(Schedulers.io())
            .compose(this.<Integer>bindToLifecycle((View) view))
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(final Integer integer) {
                    if (CurrentPlay.instance() != null) {
                        CurrentPlay.instance().newBuilder()
                            .currentTime(integer)
                            .build();
                    }
                }
            });

        view.observeVolumeSeeks()
            .observeOn(Schedulers.io())
            .compose(this.<Integer>bindToLifecycle((View) view))
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(final Integer integer) {
                    if (CurrentPlay.instance() != null) {
                        AudioManager audioManager =
                            (AudioManager) getContext().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            (int) (integer * audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 100.0),
                            AudioManager.FLAG_SHOW_UI); // TODO Remove this flag later please

                        CurrentPlay.instance().newBuilder()
                            .volume(integer)
                            .build();
                    }
                }
            });

        view.observePreviousSongClicks()
            .observeOn(Schedulers.io())
            .compose(this.<Void>bindToLifecycle((View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void integer) {
                    if (CurrentPlay.instance() != null) {
                        // TODO Should it skip the repeat mode ?? Or should it handle it ? We will skip it
                        Song nextSong = CurrentPlay.instance().playlist().size() > 1 ?
                            CurrentPlay.instance().playlist().get(1) :
                            CurrentPlay.instance().playlist().get(0);
                        List<Song> playlist = CurrentPlay.instance().playlist();

                        if (CurrentPlay.instance().repeat() == CurrentPlay.RepeatMode.ALL ||
                            CurrentPlay.instance().playlist().size() == 1) {
                            playlist.add(playlist.get(0));
                        }

                        playlist.remove(0);

                        CurrentPlay.instance().newBuilder()
                            .currentSong(nextSong)
                            .playlist(playlist)
                            .build();
                    }
                }
            });

        view.observeNextSongClicks()
            .observeOn(Schedulers.io())
            .compose(this.<Void>bindToLifecycle((View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void integer) {
                    if (CurrentPlay.instance() != null) {
                        // TODO Should it skip the repeat mode ?? Or should it handle it ? We will skip it
                        Song nextSong = CurrentPlay.instance().playlist().size() > 1 ?
                            CurrentPlay.instance().playlist().get(CurrentPlay.instance().playlist().size() - 1) :
                            CurrentPlay.instance().playlist().get(0);
                        List<Song> playlist = CurrentPlay.instance().playlist();

                        playlist.add(0, nextSong);

                        playlist.remove(playlist.size() - 1);

                        CurrentPlay.instance().newBuilder()
                            .currentSong(nextSong)
                            .playlist(playlist)
                            .build();
                    }
                }
            });


        view.observePlayStateClicks()
            .observeOn(Schedulers.io())
            .compose(this.<Void>bindToLifecycle((View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void integer) {
                    if (CurrentPlay.instance() != null) {
                        CurrentPlay.instance().newBuilder()
                            .playState(CurrentPlay.instance().playState() == CurrentPlay.PlayState.PLAYING ?
                                CurrentPlay.PlayState.PAUSED :
                                CurrentPlay.PlayState.PLAYING)
                            .build();
                    }
                }
            });

    }

    @Override
    protected void onViewRequested(@NonNull final MusicPlayerContract.View view) {
        super.onViewRequested(view);

        if (CurrentPlay.instance() != null) {
            view.setCurrentPlay(CurrentPlay.instance());
        }
    }

}
