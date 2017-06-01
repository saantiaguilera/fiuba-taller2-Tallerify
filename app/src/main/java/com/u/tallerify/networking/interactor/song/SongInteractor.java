package com.u.tallerify.networking.interactor.song;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.model.Rating;
import com.u.tallerify.model.ResolvedUri;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.RestClient;
import com.u.tallerify.networking.services.songs.SongService;
import java.util.List;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 3/19/17.
 */
@SuppressWarnings("unchecked")
public final class SongInteractor {

    public static final int ACTION_LOADING = 0;
    public static final int ACTION_EMPTY_SEARCH = 1;

    private static final @NonNull SongInteractor instance = new SongInteractor();

    @NonNull BehaviorSubject<ReactiveModel<Song>> songSubject;
    @NonNull BehaviorSubject<ReactiveModel<List<Song>>> trendingSongsSubject;
    @NonNull PublishSubject<ReactiveModel<List<Song>>> searchSubject;

    private SongInteractor() {
        songSubject = BehaviorSubject.create();
        trendingSongsSubject = BehaviorSubject.create();
        searchSubject = PublishSubject.create();
    }

    public static @NonNull SongInteractor instance() {
        return instance;
    }

    @NonNull
    public Observable<ReactiveModel<Song>> observeSong() {
        return songSubject;
    }

    @NonNull
    public Observable<ReactiveModel<List<Song>>> observeSearches() {
        return searchSubject;
    }

    @NonNull
    public Observable<ReactiveModel<List<Song>>> observeTrendingSongs() {
        return trendingSongsSubject;
    }

    public @NonNull Observable<Song> song(@NonNull Context context, long songId) {
        return RestClient.with(context).create(SongService.class)
            .song(songId)
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    songSubject.onNext(new ReactiveModel.Builder<Song>()
                        .action(ACTION_LOADING)
                        .build());
                }
            }).doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    songSubject.onNext(new ReactiveModel.Builder<Song>()
                        .error(throwable)
                        .build());
                }
            }).doOnNext(new Action1<Song>() {
                @Override
                public void call(final Song song) {
                    songSubject.onNext(new ReactiveModel.Builder<Song>()
                        .model(song)
                        .build());
                }});
    }

    public @NonNull Observable<List<Song>> recommendeds(@NonNull Context context) {
        return RestClient.with(context).create(SongService.class)
            .recommendedSongs()
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    trendingSongsSubject.onNext(new ReactiveModel.Builder<List<Song>>()
                        .action(ACTION_LOADING)
                        .build());
                }
            }).doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    trendingSongsSubject.onNext(new ReactiveModel.Builder<List<Song>>()
                        .error(throwable)
                        .build());
                }
            }).doOnNext(new Action1<List<Song>>() {
                @Override
                public void call(final List<Song> songs) {
                    trendingSongsSubject.onNext(new ReactiveModel.Builder<List<Song>>()
                        .model(songs)
                        .build());
                }
            });
    }

    public @NonNull Observable<List<Song>> search(@NonNull Context context, @NonNull String query) {
        if (query.isEmpty()) {
            return Observable.just(null)
                .map(new Func1<Object, List<Song>>() {
                    @Override
                    public List<Song> call(final Object o) {
                        return null;
                    }
                })
                .doOnNext(new Action1<List<Song>>() {
                    @Override
                    public void call(final List<Song> o) {
                        searchSubject.onNext(new ReactiveModel.Builder<List<Song>>()
                            .action(ACTION_EMPTY_SEARCH)
                            .build());
                    }
                });
        } else {
            return RestClient.with(context).create(SongService.class)
                .querySongs(query)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        searchSubject.onNext(new ReactiveModel.Builder<List<Song>>()
                            .action(ACTION_LOADING)
                            .build());
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        searchSubject.onNext(new ReactiveModel.Builder<List<Song>>()
                            .error(throwable)
                            .build());
                    }
                }).doOnNext(new Action1<List<Song>>() {
                    @Override
                    public void call(final List<Song> songs) {
                        Observable.from(songs)
                            .observeOn(Schedulers.io())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Action1<Song>() {
                                @Override
                                public void call(final Song song) {
                                    songSubject.onNext(new ReactiveModel.Builder<>()
                                        .model(song)
                                        .build());
                                }
                            });

                        searchSubject.onNext(new ReactiveModel.Builder<List<Song>>()
                            .model(songs)
                            .build());
                    }
                });
        }
    }

    public @NonNull Observable<Song> like(@NonNull Context context, @NonNull Song song) {
        return RestClient.with(context).create(SongService.class)
            .likeSong(song.id());
    }

    public @NonNull Observable<Song> dislike(@NonNull Context context, @NonNull final Song song) {
        return RestClient.with(context).create(SongService.class)
            .dislikeSong(song.id())
            .map(new Func1<Void, Song>() {
                @Override
                public Song call(final Void aVoid) {
                    return song;
                }
            });
    }

    public @NonNull Observable<Rating> rate(@NonNull Context context, @NonNull final Song song, int rate) {
        return RestClient.with(context).create(SongService.class)
            .rateSong(song.id(), rate)
            .map(new Func1<Rating, Rating>() {
                @Override
                public Rating call(final Rating rating) {
                    return rating.newBuilder()
                        .song(song)
                        .build();
                }
            });
    }

    public @NonNull Observable<Rating> rate(@NonNull Context context, @NonNull final Song song) {
        return RestClient.with(context).create(SongService.class)
            .rateSong(song.id())
            .map(new Func1<Rating, Rating>() {
                @Override
                public Rating call(final Rating rating) {
                    return rating.newBuilder()
                        .song(song)
                        .build();
                }
            });
    }

    public @NonNull Observable<ResolvedUri> resolve(@NonNull Context context, @NonNull final Song song) {
        return RestClient.with(context).create(SongService.class)
            .resolve(song.id());
    }

}
