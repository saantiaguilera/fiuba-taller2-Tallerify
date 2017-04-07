package com.u.tallerify.presenter.base.cards;

import android.support.annotation.NonNull;
import com.u.tallerify.R;
import com.u.tallerify.contract.base.cards.PlayableCardContract;
import com.u.tallerify.model.entity.Album;
import com.u.tallerify.model.entity.Artist;
import com.u.tallerify.model.entity.Playable;
import com.u.tallerify.model.entity.Playlist;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.album.AlbumInteractor;
import com.u.tallerify.networking.interactor.artist.ArtistInteractor;
import com.u.tallerify.networking.interactor.playlist.PlaylistInteractor;
import com.u.tallerify.networking.interactor.song.SongInteractor;
import com.u.tallerify.utils.BussinessUtils;
import com.u.tallerify.utils.CurrentPlay;
import com.u.tallerify.utils.adapter.GenericAdapter;
import java.util.Collections;
import java.util.List;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * This class is really bad done because of laziness.
 * Created by saguilera on 3/12/17.
 */
public class PlayableCardPresenter extends GenericAdapter.ItemPresenter<PlayableCardContract.View>
        implements PlayableCardContract.Presenter {

    // For visibility
    public static final int ACTION_TRUE = 0b0001;
    public static final int ACTION_FALSE = ~ACTION_TRUE; //1110
    public static final int ACTION_BLOCKED = 0b0010;
    private static final int ACTION_TRUE_BLOCKED = ACTION_TRUE|ACTION_BLOCKED; //0011
    public static final int ACTION_DISABLED = 0b0100;

    @NonNull Playable playable;
    int status;

    public PlayableCardPresenter(@NonNull Playable playable, int status) {
        this.playable = playable;
        this.status = status;
    }

    @Override
    protected void onAttach(@NonNull final PlayableCardContract.View view) {
        render(view);

        view.observeActionClicks()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<Void>bindToLifecycle())
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    onActionClicked();
                }
            });

        view.observePlayClicks()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<Void>bindToLifecycle())
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                   onPlayAsync(playable);
                }
            });

        // TODO el add a una playlist
    }

    @Override
    protected void onViewRequested(@NonNull final PlayableCardContract.View view) {
        super.onViewRequested(view);
        render(view);
    }

    void render(PlayableCardContract.View view) {
        view.setImage(playable.pictures() == null ? null : playable.pictures().get(0));
        view.setName(playable.fullName());

        switch (status) {
            case ACTION_BLOCKED:
            case ACTION_DISABLED:
                view.setActionEnabled(false);
                break;

            case ACTION_FALSE:
                view.setActionEnabled(true);
                view.setAction(R.drawable.ic_favorite_border_black_36dp);
                break;

            case ACTION_TRUE_BLOCKED:
            case ACTION_TRUE:
                view.setActionEnabled(true);
                // TODO for a next iteration change this for a contract method
                if (playable instanceof Playlist) {
                    view.setAction(R.drawable.ic_delete_forever_black_36dp);
                } else {
                    view.setAction(R.drawable.ic_favorite_black_36dp);
                }
        }
    }

    void onPlayAsync(@NonNull Playable playable) {
        // TODO this should be refactored but I do it like this because of laziness
        Observable<List<Song>> observable = null;
        if (playable instanceof Artist) {
            observable = ArtistInteractor.instance()
                .songs(getContext(), (Artist) playable);
        }

        if (playable instanceof Song) {
            observable = Observable.just(playable.asPlaylist());
        }

        if (playable instanceof Album) {
            observable = AlbumInteractor.instance()
                .songs(getContext(), (Album) playable);
        }

        if (playable instanceof Playlist) {
            observable = PlaylistInteractor.instance()
                .songs(getContext(), (Playlist) playable);
        }

        if (observable != null) {
            observable.observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .compose(PlayableCardPresenter.this.<List<Song>>bindToLifecycle())
                .subscribe(new Action1<List<Song>>() {
                    @Override
                    public void call(final List<Song> songs) {
                        Song currentSong = songs.get(0);
                        songs.remove(0);
                        CurrentPlay.defaults(getContext())
                            .playlist(songs)
                            .currentSong(currentSong)
                            .currentTime(0)
                            .playState(CurrentPlay.PlayState.PLAYING)
                            .build();
                    }
                });
        }
    }

    void onActionClicked() {
        // TODO this should be refactored but I do it like this because of laziness
        Observable<?> observable = null;
        if (playable instanceof Playlist) {
            observable = PlaylistInteractor.instance()
                .delete(getContext(), (Playlist) playable);
        }

        if (playable instanceof Artist) {
            if (status == ACTION_TRUE || status == ACTION_TRUE_BLOCKED) {
                observable = ArtistInteractor.instance()
                    .unfollow(getContext(), (Artist) playable);
            } else {
                observable = ArtistInteractor.instance()
                    .follow(getContext(), (Artist) playable);
            }

            if (status != ACTION_TRUE_BLOCKED) {
                status = ~status;
                requestView();
            }
        }

        if (playable instanceof Song) {
            if (status == ACTION_TRUE || status == ACTION_TRUE_BLOCKED) {
                observable = SongInteractor.instance()
                    .dislike(getContext(), (Song) playable);
            } else {
                observable = SongInteractor.instance()
                    .like(getContext(), (Song) playable);
            }

            if (status != ACTION_TRUE_BLOCKED) {
                status = ~status;
                requestView();
            }
        }

        if (observable != null) {
            observable.observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(final Object o) {
                        BussinessUtils.requestBasicInfo(getContext());
                    }
                }, Interactors.ACTION_ERROR);
        }
    }

}
