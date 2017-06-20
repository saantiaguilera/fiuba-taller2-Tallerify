package com.u.tallerify.presenter.playlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.u.tallerify.contract.playlist.AddToPlaylistContract;
import com.u.tallerify.model.entity.Playlist;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.networking.interactor.playlist.PlaylistInteractor;
import com.u.tallerify.presenter.Presenter;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 4/8/17.
 */
public class AddToPlaylistPresenter extends Presenter<AddToPlaylistContract.View>
        implements AddToPlaylistContract.Presenter {

    @Nullable List<Playlist> userPlaylists;

    @Nullable User me;

    @Nullable PublishSubject<Playlist> playlistSubject;

    boolean inputEnabled = true;

    @Override
    protected void onAttach(@NonNull final AddToPlaylistContract.View view) {
        super.onAttach(view);
        me = MeInteractor.instance().userSnapshot();
        userPlaylists = MeInteractor.instance().playlistsSnapshot();

        view.observePlaylistCreations()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<String>bindToLifecycle())
            .subscribe(new Action1<String>() {
                @Override
                public void call(final String name) {
                    // Create playlist with that name
                    inputEnabled = false;
                    requestRender();
                    PlaylistInteractor.instance().create(
                            getContext(),
                            new Playlist.Builder()
                                .name(name)
                                .description("")
                                .tracks(new ArrayList<Song>())
                                .owner(me)
                                .id(0)
                                .build())
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.immediate())
                        .subscribe(new Action1<Playlist>() {
                            @Override
                            public void call(final Playlist playlist) {
                                if (playlistSubject != null) {
                                    playlistSubject.onNext(playlist);
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(final Throwable throwable) {
                                inputEnabled = true;
                                requestRender();
                                Observable.just(null)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<Object>() {
                                        @Override
                                        public void call(final Object o) {
                                            Interactors.showError(throwable);
                                        }
                                    });
                            }
                        });
                }
            });

        view.observePlaylistPositionClicks()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<Integer>bindToLifecycle())
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(final Integer integer) {
                    if (playlistSubject != null) {
                        playlistSubject.onNext(userPlaylists.get(integer));
                    }
                }
            });
    }

    @Override
    protected void onRender(@NonNull final AddToPlaylistContract.View view) {
        if (userPlaylists != null) {
            List<String> names = Observable.from(userPlaylists)
                .observeOn(Schedulers.immediate())
                .map(new Func1<Playlist, String>() {
                    @Override
                    public String call(final Playlist playlist) {
                        return playlist.name();
                    }
                })
                .toList()
                .toBlocking()
                .first();

            view.setPlaylists(names);
        }

        view.setEditable(inputEnabled);
    }

    @NonNull
    @Override
    public Observable<Playlist> observePlaylistSelection() {
        if (playlistSubject == null) {
            playlistSubject = PublishSubject.create();
        }
        return playlistSubject;
    }

    @Override
    public void setInputEnabled(final boolean enabled) {
        inputEnabled = enabled;
        requestRender();
    }

}
