package com.u.tallerify.networking.interactor.playlist;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.model.entity.Playlist;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.RestClient;
import com.u.tallerify.networking.services.playlist.PlaylistService;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by saguilera on 3/26/17.
 */

@SuppressWarnings("unchecked")
public class PlaylistInteractor {

    public static final int ACTION_LOADING = 0;

    private static final @NonNull PlaylistInteractor instance = new PlaylistInteractor();

    @NonNull BehaviorSubject<ReactiveModel<Playlist>> subject;

    private PlaylistInteractor() {
        subject = BehaviorSubject.create();
    }

    public static @NonNull PlaylistInteractor instance() {
        return instance;
    }

    @NonNull
    public Observable<ReactiveModel<Playlist>> observePlaylist() {
        return subject;
    }

    public @NonNull Observable<Playlist> playlist(@NonNull Context context, long id) {
        return RestClient.with(context).create(PlaylistService.class)
            .playlist(id)
            .doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    subject.onNext(new ReactiveModel.Builder<Playlist>()
                        .action(ACTION_LOADING)
                        .build());
                }
            }).doOnError(new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    subject.onNext(new ReactiveModel.Builder<Playlist>()
                        .error(throwable)
                        .build());
                }
            }).doOnNext(new Action1<Playlist>() {
                @Override
                public void call(final Playlist playlist) {
                    subject.onNext(new ReactiveModel.Builder<Playlist>()
                        .model(playlist)
                        .build());
                }});
    }

    public @NonNull Observable<Playlist> create(@NonNull Context context, @NonNull Playlist playlist) {
        return RestClient.with(context).create(PlaylistService.class)
            .create(playlist.name(), playlist.description(), playlist.creator().id());
    }

    public @NonNull Observable<Playlist> delete(@NonNull Context context, @NonNull final Playlist playlist) {
        return RestClient.with(context).create(PlaylistService.class)
            .delete(playlist.id())
            .map(new Func1<Void, Playlist>() {
                @Override
                public Playlist call(final Void aVoid) {
                    return playlist;
                }
            });
    }

    public @NonNull Observable<Playlist> add(@NonNull Context context, @NonNull Playlist playlist, @NonNull Song song) {
        return RestClient.with(context).create(PlaylistService.class)
            .addSong(playlist.id(), song.id());
    }

    public @NonNull Observable<Playlist> remove(@NonNull Context context, @NonNull final Playlist playlist, @NonNull Song song) {
        return RestClient.with(context).create(PlaylistService.class)
            .removeSong(playlist.id(), song.id())
            .map(new Func1<Void, Playlist>() {
                @Override
                public Playlist call(final Void aVoid) {
                    return playlist;
                }
            });
    }

}
