package com.u.tallerify.networking.interactor.album;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.model.entity.Album;
import com.u.tallerify.model.entity.Artist;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.RestClient;
import com.u.tallerify.networking.services.album.AlbumService;
import com.u.tallerify.networking.services.artist.ArtistService;
import java.util.List;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by saguilera on 3/19/17.
 */
@SuppressWarnings("unchecked")
public final class AlbumInteractor {

    public static final int ACTION_LOADING = 0;
    public static final int ACTION_EMPTY_SEARCH = 1;

    private static final @NonNull AlbumInteractor instance = new AlbumInteractor();

    @NonNull BehaviorSubject<ReactiveModel<Album>> albumSubject;
    @NonNull BehaviorSubject<ReactiveModel<List<Album>>> querySubject;

    private AlbumInteractor() {
        albumSubject = BehaviorSubject.create();
        querySubject = BehaviorSubject.create();
    }

    public static @NonNull AlbumInteractor instance() {
        return instance;
    }

    @NonNull
    public Observable<ReactiveModel<List<Album>>> observeSearches() {
        return querySubject;
    }

    @NonNull
    public Observable<ReactiveModel<Album>> observeAlbum() {
        return albumSubject;
    }

    public @NonNull Observable<Album> album(@NonNull Context context, long albumId) {
        return RestClient.with(context).create(AlbumService.class)
            .album(albumId)
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    albumSubject.onNext(new ReactiveModel.Builder<Album>()
                        .action(ACTION_LOADING)
                        .build());
                }
            }).doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    albumSubject.onNext(new ReactiveModel.Builder<Album>()
                        .error(throwable)
                        .build());
                }
            }).doOnNext(new Action1<Album>() {
                @Override
                public void call(final Album album) {
                    albumSubject.onNext(new ReactiveModel.Builder<Album>()
                        .model(album)
                        .build());
                }});
    }

    public @NonNull Observable<List<Album>> search(@NonNull Context context, @NonNull String query) {
        if (query.isEmpty()) {
            return Observable.just(null)
                .map(new Func1<Object, List<Album>>() {
                    @Override
                    public List<Album> call(final Object o) {
                        return null;
                    }
                })
                .doOnNext(new Action1<List<Album>>() {
                    @Override
                    public void call(final List<Album> o) {
                        querySubject.onNext(new ReactiveModel.Builder<List<Song>>()
                            .action(ACTION_EMPTY_SEARCH)
                            .build());
                    }
                });
        } else {
            return RestClient.with(context).create(AlbumService.class)
                .queryAlbums(query)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        querySubject.onNext(new ReactiveModel.Builder<List<Album>>()
                            .action(ACTION_LOADING)
                            .build());
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        querySubject.onNext(new ReactiveModel.Builder<List<Album>>()
                            .error(throwable)
                            .build());
                    }
                }).doOnNext(new Action1<List<Album>>() {
                    @Override
                    public void call(final List<Album> albums) {
                        Observable.from(albums)
                            .observeOn(Schedulers.io())
                            .subscribeOn(Schedulers.io())
                            .subscribe(new Action1<Album>() {
                                @Override
                                public void call(final Album album) {
                                    albumSubject.onNext(new ReactiveModel.Builder<>()
                                        .model(album)
                                        .build());
                                }
                            });

                        querySubject.onNext(new ReactiveModel.Builder<List<Album>>()
                            .model(albums)
                            .build());
                    }
                });
        }
    }

}
