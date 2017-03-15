package com.u.tallerify.presenter.base;

import android.content.Context;
import android.content.SharedPreferences;
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

    static final String SHARED_PREFERENCES_RATING_DIR = MusicPlayerPresenter.class.getName() + "_sp_rating_dir";
    static final String SHARED_PREFERENCES_FAVS_DIR = MusicPlayerPresenter.class.getName() + "_sp_favs_dir";

    private static final String SHARED_PREFERENCES_RATING_KEY = "song_id";
    private static final String SHARED_PREFERENCES_FAVORITE_KEY = "song_id";

    public MusicPlayerPresenter() {}

    @Override
    protected void onAttach(@NonNull final MusicPlayerContract.View view) {
        render(view);

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

        // TODO observeUser and requestView();

        observeView(view);
    }

    @Override
    protected void onViewRequested(@NonNull final MusicPlayerContract.View view) {
        super.onViewRequested(view);
        render(view);
    }

    private void render(@NonNull MusicPlayerContract.View view) {
        if (CurrentPlay.instance() != null) {
            if (CurrentPlay.instance() != null) {
                CurrentPlay currentPlay = CurrentPlay.instance();
                view.setShuffleEnabled(currentPlay.shuffle());
                view.setImage(currentPlay.currentSong().album().picture());
                view.setName(currentPlay.currentSong().name(), currentPlay.currentSong().album().artist().name());
                view.setRepeatMode(currentPlay.repeat());
                view.setTime((int) currentPlay.currentTime(), (int) currentPlay.currentSong().duration());
                view.setTrackBarMax((int) currentPlay.currentSong().duration());
                view.setTrackBarProgress((int) currentPlay.currentTime());
                view.setVolume(currentPlay.volume());
                switch (currentPlay.playState()) {
                    case PLAYING:
                        view.setPlaying();
                        break;
                    case PAUSED:
                        view.setPaused();
                        break;
                }

                // TODO fill these ones when logged in ONLY. If not put enabled = false
                SharedPreferences ratingPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES_RATING_DIR, Context.MODE_PRIVATE);
                view.setRating(ratingPreferences.getInt(SHARED_PREFERENCES_RATING_KEY, 0), true);

                SharedPreferences favsPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES_FAVS_DIR, Context.MODE_PRIVATE);
                view.setFavorite(favsPreferences.getBoolean(SHARED_PREFERENCES_FAVORITE_KEY, false), true);
            }
        }
    }

    private void observeView(@NonNull MusicPlayerContract.View view) {
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

        view.observeShuffleClicks()
            .observeOn(Schedulers.io())
            .compose(this.<Void>bindToLifecycle((View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void integer) {
                    if (CurrentPlay.instance() != null) {
                        CurrentPlay.instance().newBuilder()
                            .shuffle(!CurrentPlay.instance().shuffle())
                            .build();
                    }
                }
            });

        view.observeRepeatClicks()
            .observeOn(Schedulers.io())
            .compose(this.<Void>bindToLifecycle((View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void integer) {
                    if (CurrentPlay.instance() != null) {
                        CurrentPlay.RepeatMode repeatMode;
                        switch (CurrentPlay.instance().repeat()) {
                            case NONE:
                                repeatMode = CurrentPlay.RepeatMode.SINGLE;
                                break;
                            case SINGLE:
                                repeatMode = CurrentPlay.RepeatMode.ALL;
                                break;
                            case ALL:
                            default:
                                repeatMode = CurrentPlay.RepeatMode.NONE;
                        }

                        CurrentPlay.instance().newBuilder()
                            .repeat(repeatMode)
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

        view.observeFavoriteClicks()
            .observeOn(Schedulers.io())
            .compose(this.<Void>bindToLifecycle((View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences(
                        SHARED_PREFERENCES_FAVS_DIR, Context.MODE_PRIVATE);
                    sharedPreferences.edit().putBoolean(SHARED_PREFERENCES_FAVORITE_KEY,
                        !sharedPreferences.getBoolean(SHARED_PREFERENCES_FAVORITE_KEY, false)).commit();
                    requestView();
                }
            });

        view.observeRatingSeeks()
            .observeOn(Schedulers.io())
            .compose(this.<Integer>bindToLifecycle((View) view))
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(final Integer integer) {
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences(
                        SHARED_PREFERENCES_RATING_DIR, Context.MODE_PRIVATE);
                    sharedPreferences.edit().putInt(SHARED_PREFERENCES_RATING_KEY, integer).commit();
                    requestView();
                }
            });
    }

}
