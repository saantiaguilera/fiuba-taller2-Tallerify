package com.u.tallerify.presenter.base.music_player;

import android.app.Application;
import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.u.tallerify.contract.base.music_player.MusicPlayerContract.View;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.networking.interactor.song.SongInteractor;
import com.u.tallerify.utils.CurrentPlay;
import com.u.tallerify.utils.PlayUtils;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

import static android.provider.Settings.System.CONTENT_URI;

/**
 * Created by saguilera on 3/21/17.
 */
final class RxPlayerHelper {

    static @NonNull ContentObserver bindAudioSystem(@NonNull final Application context) {
        ContentObserver contentObserver;
        context.getContentResolver()
            .registerContentObserver(CONTENT_URI, true, contentObserver = new ContentObserver(null) {

                @Override
                public void onChange(final boolean selfChange) {
                    super.onChange(selfChange);
                    AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

                    if (CurrentPlay.instance() != null) {
                        CurrentPlay.instance().newBuilder()
                            .volume(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100
                                / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC))
                            .build();
                    }
                }

                @Override
                public boolean deliverSelfNotifications() {
                    return false;
                }

            });
        return contentObserver;
    }

    static void unbindAudioSystem(@NonNull Application application, @Nullable ContentObserver contentObserver) {
        if (contentObserver != null) {
            application.getContentResolver().unregisterContentObserver(contentObserver);
        }
    }

    static void observePlayStateClicks(final View view) {
        view.observePlayStateClicks()
            .observeOn(Schedulers.computation())
            .compose(RxLifecycleAndroid.<Void>bindView((android.view.View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void integer) {
                    PlayUtils.playState();
                }
            });        
    }
    
    static void observePlaylistSkips(final View view) {
        view.observePlaylistSkipClicks()
            .observeOn(Schedulers.computation())
            .compose(RxLifecycleAndroid.<Integer>bindView((android.view.View) view))
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(final Integer integer) {
                    PlayUtils.playlistBy(integer);
                }
            });
    }
    
    static void observeTimeSeeks(final View view) {
        view.observeSongSeeks()
            .observeOn(Schedulers.computation())
            .compose(RxLifecycleAndroid.<Integer>bindView((android.view.View) view))
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(final Integer integer) {
                    PlayUtils.time(integer);
                }
            });
    }
    
    static void observeShuffleClicks(final View view) {
        view.observeShuffleClicks()
            .observeOn(Schedulers.computation())
            .compose(RxLifecycleAndroid.<Void>bindView((android.view.View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void integer) {
                    PlayUtils.shuffle();
                }
            });
    }
    
    static void observeRepeatClicks(final View view) {
        view.observeRepeatClicks()
            .observeOn(Schedulers.computation())
            .compose(RxLifecycleAndroid.<Void>bindView((android.view.View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void integer) {
                    PlayUtils.repeat();
                }
            });
    }
    
    static void observeVolumeSeeks(final Application context, final View view) {
        view.observeVolumeSeeks()
            .observeOn(Schedulers.computation())
            .compose(RxLifecycleAndroid.<Integer>bindView((android.view.View) view))
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(final Integer integer) {
                    if (CurrentPlay.instance() != null) {
                        AudioManager audioobserver =
                            (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

                        audioobserver.setStreamVolume(AudioManager.STREAM_MUSIC,
                            (int) (integer * audioobserver.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 100.0),
                            AudioManager.FLAG_SHOW_UI);

                        CurrentPlay.instance().newBuilder()
                            .volume(integer)
                            .build();
                    }
                }
            });
    }
    
    static void observeForwardClicks(final View view) {
        view.observeNextSongClicks()
            .observeOn(Schedulers.computation())
            .compose(RxLifecycleAndroid.<Void>bindView((android.view.View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void integer) {
                PlayUtils.forward();
                }
            });
    }

    @CheckResult
    static Observable<Pair<Song, Integer>> observeRatingSeeks(@NonNull final Context context,
            @NonNull final View view) {
        return view.observeRatingSeeks()
            .observeOn(Schedulers.computation())
            .compose(RxLifecycleAndroid.<Integer>bindView((android.view.View) view))
            .map(new Func1<Integer, Pair<Song, Integer>>() {
                @Override
                public Pair<Song, Integer> call(final Integer integer) {
                    return new Pair<>(CurrentPlay.instance().song(), integer);
                }
            })
            .doOnNext(new Action1<Pair<Song, Integer>>() {
                @Override
                public void call(final Pair<Song, Integer> pair) {
                    SongInteractor.instance().rate(context.getApplicationContext(), pair.first, pair.second)
                        .observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .subscribe(Interactors.ACTION_NEXT, Interactors.ACTION_ERROR);
                }
            });
    }

    @CheckResult
    static Observable<Song> observeFavoriteClicks(@NonNull final Context context,
            @NonNull final View view) {
        final BehaviorSubject<Song> subject = BehaviorSubject.create();
        view.observeFavoriteClicks()
            .observeOn(Schedulers.computation())
            .compose(RxLifecycleAndroid.<Boolean>bindView((android.view.View) view))
            .subscribe(new Action1<Boolean>() {
                @Override
                public void call(final Boolean bool) {
                    final Song song = CurrentPlay.instance().song();
                    Observable<Song> observable;

                    // Make it favorite for ux
                    subject.onNext(song);

                    if (bool) {
                        observable = SongInteractor.instance().dislike(context.getApplicationContext(), song);
                    } else {
                        observable = SongInteractor.instance().like(context.getApplicationContext(), song);
                    }

                    observable.observeOn(Schedulers.io())
                        .subscribe(new Action1<Song>() {
                            @Override
                            public void call(final Song song) {
                                // Api is bad, and gives us a song, request the favorites again for syncronizing
                                MeInteractor.instance().songs(context.getApplicationContext())
                                    .observeOn(Schedulers.io())
                                    .subscribeOn(Schedulers.io())
                                    // Not composed, this method can last whatever he wants. It has timeout also
                                    .subscribe(Interactors.ACTION_NEXT, Interactors.ACTION_ERROR);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(final Throwable throwable) {
                                // If something goes wrong, call it again
                                subject.onNext(song);
                                Interactors.showError(throwable);
                            }
                        });
                }
            });
        return subject;
    }
    
    static void observeBackwardClicks(final View view) {
        view.observePreviousSongClicks()
            .observeOn(Schedulers.computation())
            .compose(RxLifecycleAndroid.<Void>bindView((android.view.View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void integer) {
                    PlayUtils.backwards();
                }
            });
    }
    
}
