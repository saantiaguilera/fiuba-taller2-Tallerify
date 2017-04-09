package com.u.tallerify.controller.playlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.controller.abstracts.AlertDialogController;
import com.u.tallerify.model.entity.Playable;
import com.u.tallerify.model.entity.Playlist;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.networking.interactor.playlist.PlaylistInteractor;
import com.u.tallerify.presenter.abstracts.BaseDialogPresenter;
import com.u.tallerify.presenter.playlist.AddToPlaylistPresenter;
import com.u.tallerify.utils.PlayUtils;
import com.u.tallerify.view.playlist.AddToPlaylistView;
import java.util.List;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 4/8/17.
 */

public class AddToPlaylistController extends AlertDialogController {

    Playable playable;

    @Nullable Subscription subscription;

    @Nullable AddToPlaylistPresenter presenter;

    public AddToPlaylistController playable(@NonNull Playable playable) {
        this.playable = playable;
        return this;
    }

    @NonNull
    @Override
    protected View content() {
        if (playable == null) {
            throw new IllegalStateException("Forgot to attach a playable to the controller?");
        }

        View content = new AddToPlaylistView(getActivity());

        Coordinators.bind(content, new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                presenter = new AddToPlaylistPresenter();

                if (subscription != null && !subscription.isUnsubscribed()) {
                    subscription.unsubscribe();
                }

                subscription = presenter.observePlaylistSelection()
                    .observeOn(Schedulers.computation())
                    .subscribeOn(Schedulers.computation())
                    .compose(AddToPlaylistController.this.<Playlist>bindToLifecycle())
                    .subscribe(new Action1<Playlist>() {
                        @Override
                        public void call(final Playlist playlist) {
                            addToPlaylist(playlist);
                        }
                    });

                return presenter;
            }
        });

        return content;
    }

    void addToPlaylist(@NonNull final Playlist playlist) {
        PlayUtils.songs(getApplicationContext(), playable)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            // Dont compose it, if the user removes the dialog at this point try to add it anyways
            .flatMapIterable(new Func1<List<Song>, Iterable<Song>>() {
                @Override
                public Iterable<Song> call(final List<Song> songs) {
                    return songs;
                }
            })
            .flatMap(new Func1<Song, Observable<Playlist>>() {
                @Override
                public Observable<Playlist> call(final Song song) {
                    return PlaylistInteractor.instance().add(getApplicationContext(), playlist, song);
                }
            })
            // Same, dont compose them
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(Interactors.ACTION_NEXT, new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    if (presenter != null) {
                        presenter.setInputEnabled(true);
                    }
                }
            }, new Action0() {
                @Override
                public void call() {
                    // When completed, try to dismiss the dialog if still active and request the new info
                    MeInteractor.instance().playlists(getApplicationContext())
                        .observeOn(Schedulers.immediate())
                        .subscribeOn(Schedulers.immediate())
                        .subscribe(Interactors.ACTION_NEXT, Interactors.ACTION_ERROR);

                    dismissDialog();
                }
            });
    }

    @NonNull
    @Override
    protected String title() {
        return "En que playlist deseas agregarlo?";
    }

    @NonNull
    @Override
    protected BaseDialogPresenter.Severity severity() {
        return BaseDialogPresenter.Severity.INFO;
    }

}
