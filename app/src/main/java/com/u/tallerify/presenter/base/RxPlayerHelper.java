package com.u.tallerify.presenter.base;

import android.app.Application;
import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.u.tallerify.contract.base.MusicPlayerContract.View;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.networking.interactor.song.SongInteractor;
import com.u.tallerify.utils.CurrentPlay;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
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
            .observeOn(Schedulers.io())
            .compose(RxLifecycleAndroid.<Void>bindView((android.view.View) view))
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
    
    static void observePlaylistSkips(final View view) {
        view.observePlaylistSkipClicks()
            .observeOn(Schedulers.io())
            .compose(RxLifecycleAndroid.<Integer>bindView((android.view.View) view))
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(final Integer integer) {
                    if (CurrentPlay.instance() != null) {
                        final List<Song> newList = new ArrayList<>();
                        final List<Song> playlist = new ArrayList<>(CurrentPlay.instance().playlist());

                        Observable.range(0, playlist.size())
                            .doOnNext(new Action1<Integer>() {
                                @Override
                                public void call(final Integer position) {
                                    if (position >= integer - 1) {
                                        newList.add(playlist.get(position));
                                    }
                                }
                            }).doOnCompleted(new Action0() {
                            @Override
                            public void call() {
                                if (CurrentPlay.instance().repeat() == CurrentPlay.RepeatMode.ALL ||
                                    newList.isEmpty()) {
                                    for (int i = 0 ; i < integer - 1 ; ++i) {
                                        newList.add(playlist.get(i));
                                    }
                                }
                            }
                        }).toBlocking()
                            .subscribe();

                        Song nextSong = newList.get(0);
                        newList.remove(0);

                        if (CurrentPlay.instance().repeat() == CurrentPlay.RepeatMode.ALL) {
                            newList.add(nextSong);
                        }

                        CurrentPlay.instance().newBuilder()
                            .currentSong(nextSong)
                            .playlist(newList)
                            .build();
                    }
                }
            });
    }
    
    static void observeTimeSeeks(final View view) {
        view.observeSongSeeks()
            .observeOn(Schedulers.io())
            .compose(RxLifecycleAndroid.<Integer>bindView((android.view.View) view))
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
    }
    
    static void observeShuffleClicks(final View view) {
        view.observeShuffleClicks()
            .observeOn(Schedulers.io())
            .compose(RxLifecycleAndroid.<Void>bindView((android.view.View) view))
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
    }
    
    static void observeRepeatClicks(final View view) {
        view.observeRepeatClicks()
            .observeOn(Schedulers.io())
            .compose(RxLifecycleAndroid.<Void>bindView((android.view.View) view))
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
    }
    
    static void observeVolumeSeeks(final Application context, final View view) {
        view.observeVolumeSeeks()
            .observeOn(Schedulers.io())
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
            .observeOn(Schedulers.io())
            .compose(RxLifecycleAndroid.<Void>bindView((android.view.View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void integer) {
                    if (CurrentPlay.instance() != null) {
                        if (CurrentPlay.instance().playlist().isEmpty()) {
                            CurrentPlay.instance().newBuilder()
                                .playState(CurrentPlay.PlayState.PAUSED)
                                .build();
                            return;
                        }

                        List<Song> playlist = new ArrayList<>(CurrentPlay.instance().playlist());
                        Song nextSong = playlist.size() > 1 ?
                            playlist.get(1) :
                            playlist.get(0);

                        if (CurrentPlay.instance().repeat() == CurrentPlay.RepeatMode.ALL ||
                            playlist.size() == 1) {
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
    }

    @CheckResult
    static Observable<Pair<Long, Integer>> observeRatingSeeks(@NonNull final View view) {
        final BehaviorSubject<Pair<Long, Integer>> subject = BehaviorSubject.create();
        view.observeRatingSeeks()
            .observeOn(Schedulers.io())
            .compose(RxLifecycleAndroid.<Integer>bindView((android.view.View) view))
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(final Integer integer) {
                    // TODO this doesnt have any endpoint.
                    subject.onNext(new Pair<>(CurrentPlay.instance().currentSong().id(), integer));
                }
            });
        return subject;
    }

    @CheckResult
    static Observable<Song> observeFavoriteClicks(@NonNull final Application application,
            @NonNull final View view) {
        final BehaviorSubject<Song> subject = BehaviorSubject.create();
        view.observeFavoriteClicks()
            .observeOn(Schedulers.io())
            .compose(RxLifecycleAndroid.<Boolean>bindView((android.view.View) view))
            .subscribe(new Action1<Boolean>() {
                @Override
                public void call(final Boolean bool) {
                    final Song song = CurrentPlay.instance().currentSong();
                    Observable<Song> observable;

                    // Make it favorite for ux
                    subject.onNext(song);

                    if (bool) {
                        observable = SongInteractor.instance().dislike(application, song);
                    } else {
                        observable = SongInteractor.instance().like(application, song);
                    }

                    observable.observeOn(Schedulers.io())
                        .subscribe(new Action1<Song>() {
                            @Override
                            public void call(final Song song) {
                                // Api is bad, and gives us a song, request the favorites again for syncronizing
                                MeInteractor.instance().songs(application)
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
                            }
                        });
                }
            });
        return subject;
    }
    
    static void observeBackwardClicks(final View view) {
        view.observePreviousSongClicks()
            .observeOn(Schedulers.io())
            .compose(RxLifecycleAndroid.<Void>bindView((android.view.View) view))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void integer) {
                    if (CurrentPlay.instance() != null) {
                        List<Song> playlist = new ArrayList<>(CurrentPlay.instance().playlist());
                        Song nextSong = playlist.size() > 1 ?
                            playlist.get(playlist.size() - 1) :
                            playlist.get(0);

                        playlist.add(0, nextSong);

                        playlist.remove(playlist.size() - 1);

                        CurrentPlay.instance().newBuilder()
                            .currentSong(nextSong)
                            .playlist(playlist)
                            .build();
                    }
                }
            });
    }
    
}
