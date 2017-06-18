package com.u.tallerify.networking.interactor.artist;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.model.entity.Artist;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.RestClient;
import com.u.tallerify.networking.services.artist.ArtistService;
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
public final class ArtistInteractor {

    public static final int ACTION_LOADING = 0;
    public static final int ACTION_EMPTY_SEARCH = 1;

    private static final @NonNull ArtistInteractor instance = new ArtistInteractor();

    @NonNull BehaviorSubject<ReactiveModel<Artist>> artistSubject;
    @NonNull BehaviorSubject<ReactiveModel<List<Artist>>> trendingArtistsSubject;
    @NonNull PublishSubject<ReactiveModel<List<Artist>>> querySubject;

    private ArtistInteractor() {
        artistSubject = BehaviorSubject.create();
        trendingArtistsSubject = BehaviorSubject.create();
        querySubject = PublishSubject.create();
    }

    public static @NonNull ArtistInteractor instance() {
        return instance;
    }

    @NonNull
    public Observable<ReactiveModel<List<Artist>>> observeSearches() {
        return querySubject;
    }

    @NonNull
    public Observable<ReactiveModel<Artist>> observeArtist() {
        return artistSubject;
    }

    @NonNull
    public Observable<ReactiveModel<List<Artist>>> observeTrendingArtists() {
        return trendingArtistsSubject;
    }

    public @NonNull Observable<Artist> artist(@NonNull Context context, long artistId) {
        return RestClient.with(context).create(ArtistService.class)
            .artist(artistId)
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    artistSubject.onNext(new ReactiveModel.Builder<Artist>()
                        .action(ACTION_LOADING)
                        .build());
                }
            }).doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    artistSubject.onNext(new ReactiveModel.Builder<Artist>()
                        .error(throwable)
                        .build());
                }
            }).doOnNext(new Action1<Artist>() {
                @Override
                public void call(final Artist artist) {
                    artistSubject.onNext(new ReactiveModel.Builder<Artist>()
                        .model(artist)
                        .build());
                }});
    }

    public @NonNull Observable<List<Artist>> recommendeds(@NonNull Context context) {
        return RestClient.with(context).create(ArtistService.class)
            .recommendedArtists()
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    trendingArtistsSubject.onNext(new ReactiveModel.Builder<List<Artist>>()
                        .action(ACTION_LOADING)
                        .build());
                }
            }).doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    trendingArtistsSubject.onNext(new ReactiveModel.Builder<List<Artist>>()
                        .error(throwable)
                        .build());
                }
            }).doOnNext(new Action1<List<Artist>>() {
                @Override
                public void call(final List<Artist> artists) {
                    trendingArtistsSubject.onNext(new ReactiveModel.Builder<List<Artist>>()
                        .model(artists)
                        .build());
                }});
    }

    public @NonNull Observable<Artist> follow(@NonNull Context context, final @NonNull Artist artist) {
        return RestClient.with(context).create(ArtistService.class)
            .followArtist(artist.id())
            .map(new Func1<Void, Artist>() {
                @Override
                public Artist call(final Void aVoid) {
                    return artist;
                }
            });
    }

    public @NonNull Observable<Artist> unfollow(@NonNull Context context, final @NonNull Artist artist) {
        return RestClient.with(context).create(ArtistService.class)
            .unfollowArtist(artist.id())
            .map(new Func1<Void, Artist>() {
                @Override
                public Artist call(final Void aVoid) {
                    return artist;
                }
            });
    }

    public @NonNull Observable<List<Song>> songs(@NonNull Context context, final @NonNull Artist artist) {
        return RestClient.with(context).create(ArtistService.class)
            .songs(artist.id());
    }

    public @NonNull Observable<List<Artist>> search(@NonNull Context context, @NonNull String query) {
        if (query.isEmpty()) {
            return Observable.just(null)
                .map(new Func1<Object, List<Artist>>() {
                    @Override
                    public List<Artist> call(final Object o) {
                        return null;
                    }
                })
                .doOnNext(new Action1<List<Artist>>() {
                    @Override
                    public void call(final List<Artist> o) {
                        querySubject.onNext(new ReactiveModel.Builder<List<Song>>()
                            .action(ACTION_EMPTY_SEARCH)
                            .build());
                    }
                });
        } else {
            return RestClient.with(context).create(ArtistService.class)
                .queryArtists(query)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        querySubject.onNext(new ReactiveModel.Builder<List<Artist>>()
                            .action(ACTION_LOADING)
                            .build());
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        querySubject.onNext(new ReactiveModel.Builder<List<Artist>>()
                            .error(throwable)
                            .build());
                    }
                }).doOnNext(new Action1<List<Artist>>() {
                    @Override
                    public void call(final List<Artist> artists) {
                        Observable.from(artists)
                            .observeOn(Schedulers.io())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Action1<Artist>() {
                                @Override
                                public void call(final Artist artist) {
                                    artistSubject.onNext(new ReactiveModel.Builder<>()
                                        .model(artist)
                                        .build());
                                }
                            });

                        querySubject.onNext(new ReactiveModel.Builder<List<Artist>>()
                            .model(artists)
                            .build());
                    }
                });
        }
    }

}
