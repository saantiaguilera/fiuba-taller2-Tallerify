package com.u.tallerify.networking.interactor.song;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.RestClient;
import com.u.tallerify.networking.services.songs.SongService;
import java.util.List;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Created by saguilera on 3/19/17.
 */
@SuppressWarnings("unchecked")
public final class SongInteractor {

    public static final int ACTION_LOADING = 0;

    private static final @NonNull SongInteractor instance = new SongInteractor();

    @NonNull BehaviorSubject<ReactiveModel<Song>> songSubject;
    @NonNull BehaviorSubject<ReactiveModel<List<Song>>> trendingSongsSubject;

    private SongInteractor() {
        songSubject = BehaviorSubject.create();
        trendingSongsSubject = BehaviorSubject.create();
    }

    public static @NonNull SongInteractor instance() {
        return instance;
    }

    @NonNull
    public Observable<ReactiveModel<Song>> observeSong() {
        return songSubject;
    }

    @NonNull
    public Observable<ReactiveModel<List<Song>>> observeTrendingSongs() {
        return trendingSongsSubject;
    }

    public Observable<Song> song(@NonNull Context context, long songId) {
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

    public Observable<List<Song>> trendingSongs(@NonNull Context context) {
        return RestClient.with(context).create(SongService.class)
            .trendingSongs()
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
                }});
    }

}
